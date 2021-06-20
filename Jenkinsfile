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
                  sh 'mvn clean install'
				}  
            
            }
		}
		
		stage("Code Quality")
		{
		steps{
				sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
               
			}
		}
	
	}



}