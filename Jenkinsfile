pipeline {
  agent any
  stages {
    stage('Test') {
      parallel {
        stage('Test') {
          steps {
            sh './gradlew clean test'
            junit 'build/unit-test-results/**.xml'
          }
        }
        stage('Integration Test') {
          steps {
            sh './gradle clean integrationTest'
            junit 'build/integration-test-results/**.xml'
          }
        }
      }
    }
    stage('Build') {
      steps {
        sh './gradlew clean build'
      }
    }
  }
}