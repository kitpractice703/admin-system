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