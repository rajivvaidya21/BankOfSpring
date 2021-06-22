def SERVER_ID =  "jfrog"
pipeline{

agent any

  	stages{
		stage(Checkout){
		steps 
			{
			checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/rajivvaidya21/BankOfSpring']]])
            }
		}
		
		stage("build"){
				steps {
 				 sh "ls"
				  withMaven(maven : 'Maven') {
                  sh 'mvn clean install -Dmaven.test.skip=true'
				}  
            
            }
		}
		
		stage("Static Code Analysis")
		{
		steps{
		 	withMaven(maven : 'Maven') {
				sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
               }
			}
		}
		
		stage("Artifactory Upload")
		{
			steps{
				
				sh "cd BankOfSpring"
				
			    rtUpload (
				        serverId: SERVER_ID,
				        spec:
				            """{
				              "files": [
				                {
				                  "pattern": "./target/*.jar",
				              	  "target": "default-maven-local/bank/"
				                }
				             ]
				            }""",
        			failNoOp: true
    				)
			
				rtPublishBuildInfo (
				                    serverId: SERVER_ID
				                )
					sendEmail()		
			
			}
		}
			
	}
}


def sendEmail(){
    
    emailext body: "Check console output at ${env.BUILD_URL} to view the results",
    subject: "${env.JOB_BASE_NAME} # ${env.BUILD_NUMBER} - ${currentBuild.result}",
    to: 'rajivvaidya212@outlook.com'
    
}
