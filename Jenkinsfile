def appName = "hello-anthem"
def devProject = "hello-anthem-jenkins"
//def devProject = "hello-anthem-jenkins-2"
def testProject = "hello-anthem-skopeo-test"

def skopeoToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJoZWxsby1hbnRoZW0tamVua2lucyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJqZW5raW5zLXJvYm90LXRva2VuLXpkNGRyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImplbmtpbnMtcm9ib3QiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIzMjU0MDA4MC1mZjZiLTExZTktODE5MC0wYTcwZDk5NWRjMjYiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6aGVsbG8tYW50aGVtLWplbmtpbnM6amVua2lucy1yb2JvdCJ9.PjKRjRRfuPbaZYoaoFUGWQj_2qQqtzG0sDyR1Q2Xe6_dgo9vh7iXCMI2grQn_bqRvblFTVfqd7Omc-IiticCuC9dTkhhit5Clt2lxuSyo9sxpjOeVukdm07C5Ywq4v0zvxjzFMHb_YvsXbYIvvkmn2QU0STNrNfnAbgAGvfIgDufswRTAaQn_pDHHkSgjVZaKoMVjB4h48zmnnYKuZxRvrM3QYDpifahxpulAJ-6p0pc99PEhtbD3Ed_jC97OQbXi8oSr5koIfM3ErxLFCRr8tlolyAp-sm4jxLNisH6KR7TSa5C1HyCM27bwBpKwyuwV_YtmQuZSIizP5wyWpJM6g"

def skopeoTokenSrc2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJoZWxsby1hbnRoZW0tc2tvcGVvLXRlc3QiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlY3JldC5uYW1lIjoiamVua2lucy1yb2JvdC10b2tlbi1sc3hxcSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJqZW5raW5zLXJvYm90Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiYjJjOGNkZmQtMDRiOC0xMWVhLTlmMTgtMTI4NWZlMTAyMmVjIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmhlbGxvLWFudGhlbS1za29wZW8tdGVzdDpqZW5raW5zLXJvYm90In0.qt_xPc0L5VYXQjjnaBy2U8F5w6_UBqth4E9hPnZALgmhVAU9y785iR94mraLYx3vWDvLf27b1SpZ7dpTI30S1XMdfkY1J0QCKIgZFWkwDI8yD-_0PrzeyF5Hyo9PZki_bmyC8-ob6njJZ4PWtdfJW4PapxnUEQom6H9EpnGcgSJIKFghdBASuKYYjFWcdDIm1T7qGdl6WTEWTZeaXhX0FWKuJ3OKI11wQVldg2lGsr7-PUcSIVrcMllXBg8OkfxGEVgQQkVgteBPZ6fg0cgqd_pBtth_2lkWHcFnaMJ1N-iYDL_4EMvq3RdN2LXUir2lRbt3Vw3i0xsh_V1GWD787A"
 

//def skopeoToken

def imageTag = "hello-anthem:latest"

def skopeoCopy(def skopeoToken, def srcProject, def destProject, def appName, def imageTag) {
    sh """skopeo copy --src-tls-verify=false --src-creds=openshift:${skopeoToken} \
    --dest-tls-verify=false --dest-creds=openshift:${skopeoTokenSrc2} \
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
      /*stage("Setup") {
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
      }*/
      stage("Build & Test") {
          steps {
	       sh "mvn clean package"
	  }
      }
      stage("Copy Image to Test") {
          steps {
	      script {
	          skopeoCopy(skopeoToken, devProject, testProject, appName, imageTag)
		  echo "successfully copied image to test stage"
	      }
	  }
      }
   }
}
