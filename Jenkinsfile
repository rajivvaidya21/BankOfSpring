def SERVER_ID =  "jfrog"
def pushDockerImage = false
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
					
			
			}
		}
		
		stage('Push Docker Image to Artifactory'){
		     when {
		       expression { pushDockerImage == true }           
		                
		     }

		    steps{
		        
		       rtDockerPush(
			    serverId: SERVER_ID,
			    image: 'rajivvaidya.jfrog.io/default-docker-local' +'/bankofspring',
			    targetRepo: 'default-docker-local'
			   
)
		                
		    }
 
		}
		
		
		
		stage('Deploy'){
		    
		    
		     agent {
                docker {
                    image 'bankofspring'
                    reuseNode true
                }
            }
            steps {
                  rtDownload (
				        serverId: SERVER_ID,
				        spec:
				            """{
				              "files": [
				                {
				                  "pattern": "default-maven-local/bank/*.jar",
				              	  "target": "./"
				                }
				             ]
				            }""",
        			failNoOp: true
    				)
    				sh "cd BankOfSpring"
    				sh 'ls'
    				sh "cd bank"
    				sh 'java -jar bankofspring-0.0.1-SNAPSHOT.jar'
			
            }
        }
		    
			
	}
	
	
	 post {  
         
         success {  
           sendEmail("Success")		
         }  
         failure {  
            sendEmail("Failed")		  
         }  
         unstable {  
            sendEmail("Unstable")		
         }  
         changed {  
             echo 'This will run only if the state of the Pipeline has changed'  
             echo 'For example, if the Pipeline was previously failing but is now successful'  
         }  
     }  
}


def sendEmail(status){
    
    emailext body: "Check console output at ${env.BUILD_URL} to view the results",
    subject: "${env.JOB_BASE_NAME} # ${env.BUILD_NUMBER} - ${status}",
    to: 'rajivvaidya212@outlook.com'
    
}



