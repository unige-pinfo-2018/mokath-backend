pipeline {
    agent {
    	docker {
    		image 'maven:latest'
				args '-v /root/.m2:/root/.m2'
    	}
    }

    stages {

				stage('Prepare') {
					dir ('docker-config'){
						sh './run_local.sh'
					}
				}

        stage('Build') {
					steps {
						dir ('uniknowledge-rest-api') {
							sh 'mvn clean compile'
						}
        	}

        }
        stage('Test') {
            steps {
							dir ('uniknowledge-rest-api') {
								sh 'mvn test'
							}
            }
        }
        stage('Deploy') {
            steps {
							dir ('uniknowledge-rest-api') {
								sh 'mvn clean install'
							}
            }
        }
    }
}
