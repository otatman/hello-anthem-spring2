def appName = "hello-anthem-jenkins"
def devProject = "hello-anthem-jenkins"
def testProject = "hello-anthm-skpo-testapp"

def skopeoToken
def imageTag

def getVersionromPom() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}

def skopeoCopy(def skopeoToken, def srcProject, def destProject, def appName, defimageTag) {
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
      stage("Setup") {
	  steps {
	      script {
	          openshift.withCluster() {
		      openshift.withProject() {
		          skopeoToken = openshift.raw("sa get-token jenkins").out.trim()
			  println(skopeoToken)
		  } 
	      }
	  }
	}
      }
      stage("Build & Test") {
          steps {
	       sh "mvn clean package"
	  }
      }
      stage("Copy Image to Test") {
          agent { label "jenkins-agent-skopeo" }
          steps {
	      script {
	          skopeoCopy(skopeoToken, devProject, testProject, appName, imageTag)
	      }
	  }
      }
   }
}
