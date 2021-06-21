pipeline{

agent any

  	stages{
		stage(checkout){
		steps 
			{
			checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/rajivvaidya21/BankOfSpring']]])
            }
		}
		
		stage(build){
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
		 	rtUpload (
   		 serverId: 'jfrog',
    		spec: '''{
          "files": [
            {
              "pattern": "{project.basedir}/../target/bankofspring-0.0.1-SNAPSHOT.jar",
              "target": "default-maven-local"
            }
       			  	]
   				 }''',
   				buildName: 'BuildByCylock',
    			buildNumber: '1',
 
)
			}
		}
	}



}