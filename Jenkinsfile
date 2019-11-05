def appName = "hello-anthem-jenkins"

def skopeoToken
def imageTag


def skopeoCopy(def skopeoToken, def srcProject, def destProject, def appName, defimageTag) {}
    sh """skopeo copy --src-tls-verify=false --src-creds=jenkins:${skopeoToken} \
    --dest-tls-verify=false --dest-creds=jenkins:${skopeoToken} \
    docker://docker-registry.default.svc:5000/${srcProject}/${appName}:${imageTag} \
    docker://docker-registry.default.svc:5000/${destProject}/${appName}:${imageTag}"""
}




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
	       sh "mvn clean package"
	   }
      }
   }
}
