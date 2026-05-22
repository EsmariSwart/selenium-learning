pipeline {
    agent { label 'windows && selenium' }

    parameters {
        choice(name: 'SUITE', choices: ['smoke', 'regression'], description: 'Which test suite to run')
        choice(name: 'TARGET_ENV', choices: ['dev', 'qa'], description: 'Which environment profile to run')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/EsmariSwart/selenium-learning.git'
            }
        }

        stage('Verify Maven') {
            steps {
                bat 'mvn -version'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -Pheadless,${params.SUITE},${params.TARGET_ENV}"
            }
        }

        stage('Generate Allure HTML') {
            steps {
                bat 'mvn allure:report'
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'target/screenshots/**/*.png', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**/*', allowEmptyArchive: true
        }
    }
}