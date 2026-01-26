pipeline {
    agent any

    tools {
        maven 'Maven-3.9' // Assure-toi que Maven est configuré dans Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Récupération du code source...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilation du projet...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Exécution des tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analyse SonarQube...'
                // Appel direct du scanner SonarQube local
                sh 'sonar-scanner -Dproject.settings=sonar-project.properties'
            }
        }

        stage('Package') {
            steps {
                echo 'Création du JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Pipeline réussi !'
        }
        failure {
            echo 'Pipeline échoué.'
        }
        always {
            echo 'Nettoyage de l’espace de travail...'
            cleanWs()
        }
    }
}
