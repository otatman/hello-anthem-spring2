def appName = "hello-anthem"
def devProject = "hello-anthem-jenkins"
def testProject = "hello-anthem-skopeo-test"
 

def imageTag = "hello-anthem:latest"

def skopeoCopy(def skopeoTokenSrc, def skopeoTokenDest, def srcProject, def destProject, def appName, def imageTag) {
    sh """skopeo copy --src-tls-verify=false --src-creds=openshift:${skopeoTokenSrc} \
    --dest-tls-verify=false --dest-creds=openshift:${skopeoTokenDest} \
    docker://docker-registry-default.apps.ent-ocp-np1-useast1.aws.internal.das/${srcProject}/${imageTag} \
    docker://docker-registry-default.apps.ent-ocp-np2-useast1.aws.internal.das/${destProject}/${imageTag}"""
}


pipeline {
  agent {
    node {
      label 'skopeo-agent' 
    }
  }

  stages {
      stage("Setup Source") {
	  steps {
	      script {
	          openshift.withCluster() {
		      openshift.withProject("hello-anthem-jenkins") {
		      	  skopeoTokenSrc = openshift.raw("sa get-token jenkins").out.trim()
			  println(skopeoTokenSrc)
		      }
		}
	    }
	}
      }
      stage("Setup Destination") {
      	  steps {
	      script {
	      	  openshift.withCluster() {
		      openshift.withProject() {
		          skopeoTokenDest = openshift.raw("sa get-token jenkins-sa").out.trim()
			  println(skopeoTokenDest)
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
          steps {
	      script {
	          skopeoCopy(skopeoTokenSrc, skopeoTokenDest, devProject, testProject, appName, imageTag)
		  echo "successfully copied image to test stage"
	      }
	  }
      }
   }
}
