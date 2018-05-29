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
							sh 'mvn -B -DskipTests clean package'
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
