def appName = "hello-anthem"
def devProject = "hello-anthem-jenkins"
def testProject = "hello-anthem-skopeo-test"

def skopeoToken
def imageTag = "hello-anthem:latest"


def skopeoCopy(def skopeoToken, def srcProject, def destProject, def appName, def imageTag) {
    sh """skopeo copy --src-tls-verify=false --src-creds=jenkins:${skopeoToken} \
    --dest-tls-verify=false --dest-creds=jenkins:${skopeoToken} \
    docker://docker-registry-default.master.ent-ocp1-np1-useast1.aws.internal.das/console/${srcProject}/${imageTag} \
    docker://master.ent-ocp-np2.aws.internal.das/console/${destProject}/${imageTag}"""
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
          agent { label "skopeo-agent" }
          steps {
	      script {
	          skopeoCopy(skopeoToken, devProject, testProject, appName, imageTag)
	      }
	  }
      }
   }
}
