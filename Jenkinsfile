def appName = "hello-anthem"
def devProject = "hello-anthem-jenkins"
def testProject = "hello-anthem-skopeo-test"

/*def skopeoTokenSrc = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJoZWxsby1hbnRoZW0tamVua2lucyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJqZW5raW5zLXJvYm90LXRva2VuLXpkNGRyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImplbmtpbnMtcm9ib3QiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIzMjU0MDA4MC1mZjZiLTExZTktODE5MC0wYTcwZDk5NWRjMjYiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6aGVsbG8tYW50aGVtLWplbmtpbnM6amVua2lucy1yb2JvdCJ9.PjKRjRRfuPbaZYoaoFUGWQj_2qQqtzG0sDyR1Q2Xe6_dgo9vh7iXCMI2grQn_bqRvblFTVfqd7Omc-IiticCuC9dTkhhit5Clt2lxuSyo9sxpjOeVukdm07C5Ywq4v0zvxjzFMHb_YvsXbYIvvkmn2QU0STNrNfnAbgAGvfIgDufswRTAaQn_pDHHkSgjVZaKoMVjB4h48zmnnYKuZxRvrM3QYDpifahxpulAJ-6p0pc99PEhtbD3Ed_jC97OQbXi8oSr5koIfM3ErxLFCRr8tlolyAp-sm4jxLNisH6KR7TSa5C1HyCM27bwBpKwyuwV_YtmQuZSIizP5wyWpJM6g"*/


def skopeoTokenDest
def imageTag = "hello-anthem:latest"
//def imageTag = "hello-anthem"

def skopeoCopy(def skopeoTokenSrc, def srcProject, def destProject, def appName, def imageTag) {
    sh """skopeo copy --src-tls-verify=false --src-creds=jenkins:${skopeoTokenSrc} \
    --dest-tls-verify=false --dest-creds=jenkins:${skopeoTokenSrc} \
    docker://docker-registry-default.apps.ent-ocp-np1-useast1.aws.internal.das/${srcProject}/${imageTag} \
    docker://docker-registry-default.apps.ent-ocp-np2-useast1.aws.internal.das/${destProject}/${imageTag}"""
}


pipeline {
  agent {
    node {
      label 'skopeo-agent' 
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
      stage("Copy Image to Test") {
          steps {
	      script {
	          #skopeoCopy(skopeoTokenSrc, devProject, testProject, appName, imageTag)
		  echo "got to copy image to test stage"
	      }
	  }
      }
   }
}
