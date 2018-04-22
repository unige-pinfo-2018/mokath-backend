pipeline {
    agent {
    	docker {
    		image 'maven:latest'
				args '-v /root/.m2:/root/.m2'
    	}
    }

    stages {
        stage('Build') {
<<<<<<< HEAD
					steps {
						dir ('uniknowledge-rest-api') {
							sh 'mvn clean install'
						}
        	}

=======
            steps {
                echo 'Building..'
            }
						dir ('uniknowledge-rest-api') {
							sh 'mvn clean install'
						}
>>>>>>> 6857a1515f02e3665a199711b792ab34a318296a
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
