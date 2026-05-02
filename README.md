# Assignment 3 — Cool Jetpack Weather App

Course: Desenvolvimento de Aplicações Móveis
Student(s): Guilherme Graça A51827
Date: 2 de Maio de 2026
Repository URL: https://github.com/GuilhermeGraca/DAM_TP3_CoolJetpackWeatherApp/
---

## 1. Introduction
O objetivo deste projeto foi reconstruir a aplicação WeatherApp do tutorial anterior. A nova versão adaptou-se à arquitetura MVVM. A interface foi construída com a tecnologia Jetpack Compose em vez de layouts XML.

## 2. System Overview
A aplicação permite consultar dados de meteorologia de várias localizações. O sistema apresenta a temperatura, o vento e a pressão atmosférica. Adicionou-se um mapa para escolher locais e uma lista de localizações favoritas. O projeto suporta os idiomas inglês e português.

## 3. Architecture and Design
O projeto segue a arquitetura MVVM. O código da aplicação unscramble referido no enunciado foi utilizado como código base na montagem do MVVM. A estrutura divide-se em três pacotes de ficheiros. O pacote data gere o acesso aos dados da API. O pacote viewmodel guarda o estado da aplicação. O pacote ui guarda os elementos visuais.

## 4. Implementation
A implementação da interface utilizou o Jetpack Compose com funções independentes. Desenvolveram-se vistas para os modos retrato e paisagem. A comunicação com a API utilizou a biblioteca Ktor.

## 5. Testing and Validation
Validou-se a alteração de orientação do dispositivo. Confirmou-se também o funcionamento correto da mudança de idioma.

## 6. Usage Instructions
Para executar o projeto precisa-se do Android Studio. Abre-se a pasta do projeto no programa e compila-se para um dispositivo Android. É preciso configurar uma chave da API do Google Maps para o mapa funcionar.

---
# Development Process
## 12. Version Control and Commit History
Utilizou-se a ferramenta Git para fazer o controlo de versões. O trabalho foi guardado por etapas ao longo das semanas de desenvolvimento.

## 13. Difficulties and Lessons Learned
A maior dificuldade foi reaprender a como fazer os designs agora com o Jetpack Compose. No entanto, foi aonde se aprendeu mais.

## 14. Future Improvements
Pode-se acrescentar mais previsões do tempo para os dias seguintes. Pode-se também guardar a informação no telemóvel para funcionar sem internet.

---
## 15. AI Usage Disclosure (Mandatory)
Utilizou-se o NotebookLM com os slides da cadeira. O Nano Banana foi usado para criar o ícone da app. O autocomplete foi utilizado para auxiliar na escrita de código, verificando a sintaxe e sugerindo implementações. O Gemini 3 foi utilizado para auxiliar na redação deste README. Declara-se responsabilidade por todo o conteúdo.
