pipeline {
  agent any

  environment {
    JWT_SECRET = credentials('jwt-secret')
    SONAR_TOKEN = credentials('sonarqube-token')
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('GitLeaks') {
      steps {
        sh 'gitleaks detect --source $WORKSPACE --no-git'
      }
    }

    stage('auth-service Build') {
      steps {
        dir('admin-backend') {
          sh 'chmod +x ./gradlew'
          sh './gradlew :auth-service:build -x test'
        }
      }
    }

    stage('user-service Build') {
      steps {
        dir('admin-backend') {
          sh './gradlew :user-service:build -x test'
        }
      }
    }

    stage('auth-service Test & Coverage') {
      steps {
        withEnv(['SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/auth_db']) {
          dir('admin-backend') {
            sh './gradlew :auth-service:test :auth-service:jacocoTestReport'
          }
        }
      }
    }

    stage('user-service Test & Coverage') {
      steps {
        withEnv(['SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/user_db']) {
          dir('admin-backend') {
            sh './gradlew :user-service:test :user-service:jacocoTestReport'
          }
        }
      }
    }

    stage('content-service Build') {
      steps {
        dir('admin-backend') {
          sh './gradlew :content-service:build -x test'
        }
      }
    }

    stage('content-service Test & Coverage') {
      steps {
        withEnv(['SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/content_db']) {
          dir('admin-backend') {
            sh './gradlew :content-service:test :content-service:jacocoTestReport'
          }
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        dir('admin-backend') {
          sh './gradlew sonar -Dsonar.projectKey=admin-backend -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=$SONAR_TOKEN'
        }
      }
    }

    stage('Docker Build & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'ocir-credentials', usernameVariable: 'OCIR_USER', passwordVariable: 'OCIR_PASS')]) {
          sh 'echo "$OCIR_PASS" | docker login yny.ocir.io -u "$OCIR_USER" --password-stdin'
          sh 'docker buildx create --name oke-builder --driver docker-container --bootstrap --use || docker buildx use oke-builder'
          sh 'docker buildx build --platform linux/arm64 -t yny.ocir.io/axspvtyyqq6h/auth-service:latest -f admin-backend/auth-service/Dockerfile --push admin-backend'
          sh 'docker buildx build --platform linux/arm64 -t yny.ocir.io/axspvtyyqq6h/user-service:latest -f admin-backend/user-service/Dockerfile --push admin-backend'
          sh 'docker buildx build --platform linux/arm64 -t yny.ocir.io/axspvtyyqq6h/content-service:latest -f admin-backend/content-service/Dockerfile --push admin-backend'
        }
      }
    }    

    stage('Deploy to OKE') {
      steps {
        withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
          sh 'kubectl apply -f k8s/auth-service.yaml'
          sh 'kubectl apply -f k8s/user-service.yaml'
          sh 'kubectl apply -f k8s/content-service.yaml'
          sh 'kubectl rollout restart deployment/auth-service deployment/user-service deployment/content-service'
        }
      }
    }
  }

  post {
    success {
      echo 'Build succeeded.'
    }
    failure {
      echo 'Build failed.'
    }
  }

}