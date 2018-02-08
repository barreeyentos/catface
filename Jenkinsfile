pipeline {
  agent any
  stages {
    stage('Checkout') {
      agent any
      steps {
        git(url: 'git@github.com:barreeyentos/catface.git', branch: 'master')
      }
    }
    stage('Test') {
      parallel {
        stage('Test') {
          steps {
            sh './gradlew clean test'
          }
        }
        stage('Integration Test') {
          steps {
            sh './gradle clean integrationTest'
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