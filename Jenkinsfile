pipeline {
  agent {
    node {
      label 'maven' 
    }
  }
  options {
    timeout(time: 20, unit: 'MINUTES') 
  }
  stages {
      stage("Build & Test") {
           steps {
	       input "Ready to start?"
	   }
      }
   }
}
