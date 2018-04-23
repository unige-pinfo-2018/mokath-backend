pipeline {
    agent {
    	docker {
    		image 'maven:latest'
				args '-v /root/.m2:/root/.m2'
    	}
    }

    stages {
        stage('Build') {
					steps {
						dir ('uniknowledge-rest-api') {
							sh 'mvn clean install'
						}
        	}

        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
