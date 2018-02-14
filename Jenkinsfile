pipeline {
  agent any
  stages {
    stage('Test') {
      parallel {
        stage('Test') {
          steps {
            sh './gradlew test'
            junit 'build/unit-test-results/**.xml'
          }
        }
        stage('Integration Test') {
          steps {
            sh './gradlew integrationTest'
            junit 'build/integration-test-results/**.xml'
          }
        }
      }
    }
    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }
  }
}