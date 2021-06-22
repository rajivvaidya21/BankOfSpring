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
				        serverId: "jfrog",
				        spec:
				            """{
				              "files": [
				                {
				                  "pattern": "./target/bankofspring-0.0.1-SNAPSHOT.jar",
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
	}



}