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

    stage('Build') {
      steps {
        dir('admin-backend') {
          sh 'chmod +x ./gradlew'
          sh './gradlew :auth-service:build -x test'
        }
      }
    }

    stage('Test & Coverage') {
      steps {
        dir('admin-backend') {
          sh './gradlew :auth-service:test :auth-service:jacocoTestReport -Dspring.datasource.url=jdbc:postgresql://admin-postgres:5432/auth_db'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        dir('admin-backend') {
          sh "./gradlew sonar -Dsonar.projectKey=auth-service -Dsonar.host.url=http://admin-sonarqube:9000 -Dsonar.token=${SONAR_TOKEN}"
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