# Aplicativo MarvelExplorer

### Marvel Explorer é o aplicativo Android para todos os fãs do universo Marvel. Com uma interface intuitiva e recursos poderosos, permite aos usuários explorar o vasto mundo dos super-heróis da Marvel de uma maneira envolvente e informativa.

<br>

<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/2c44de89-6cb0-4a4b-a5e2-08fb5be37327" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/a3a5af08-3f9b-4599-b54d-90bde684df4f" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/58641f96-71d0-4d0b-9d1f-8d9c1471f376" width="250"/>

<br>

## 🚀 Intro

* Exploração de Personagens: Os usuários podem explorar uma extensa galeria de personagens da Marvel, incluindo heróis, vilões e anti-heróis. Cada personagem vem com uma biografia detalhada, estatísticas de poder e uma lista de histórias em quadrinhos em que aparecem.

* Histórias em Quadrinhos: Acesso a uma biblioteca completa de histórias em quadrinhos da Marvel, permitindo aos usuários lerem suas histórias favoritas direto do aplicativo. Os usuários podem pesquisar por título, personagem ou evento específico.

* Notícias e Atualizações: Fique por dentro das últimas notícias, lançamentos de filmes, séries de TV e eventos relacionados ao universo Marvel. Receba notificações sobre novos lançamentos e atualizações importantes.

* Eventos e Cronologia: Explore eventos importantes do universo Marvel e sua cronologia, permitindo aos usuários entenderem melhor a ordem dos acontecimentos e como as histórias se interligam.

* Feed: Sessão que exibe todos as atualizações diarias da Marvel

* Busca Avançada: Um mecanismo de busca que permite aos usuários encontrar rapidamente personagens, histórias em quadrinhos, eventos e muito mais.

* Integração com o Portal Marvel: O aplicativo utiliza a API oficial da Marvel para obter dados precisos e atualizados sobre personagens, histórias em quadrinhos e outros conteúdos, garantindo uma experiência confiável e autêntica.

### 📋 O que usei!

* Framework: Android
* Framework Version:  [ Jellyfish | 2023.3.1 Canary 12 (Preview Release)](https://developer.android.com/studio/preview?_gl=1*d01t76*_up*MQ..&gclid=CjwKCAiA3JCvBhA8EiwA4kujZipwbeiyH0o3C5bHRhDQsgY2_TTLx-dcu0MpfaGqGGGmOl-ntK2UDRoCeA0QAvD_BwE&gclsrc=aw.ds)
* Linguagem: Kotlin
* Arquitetura: MVVM
* Device: Emulador PIXEL 8 Pro API 30
* Database: SQLite (DAO)
* Injeção de dependência: Hilt

### 🔧 Intalação

* Deve criar uma conta no [Portal da Marvel](https://developer.marvel.com/)
* Deve inserir suas chaves que foi gerada no portal da marvel no arquivo `Util/Keymarvel.kt`

### ℹ Informações

* A HOME foi construida localmente para economizar requisição, já que o portal da Marvel tem um limite diario de requests.
* O FEED foi construido buscando os 60 itens mais recentes que é registrado como modificação pela Marvel. Comics, Character e event (20 de cada)
* Os favoritos permanecem localmente enquanto o app está instalado
* Os favoritos são excluidos quando arrastam o item para o lado.
* Os menus de Comics, Characters e Events estão integrados. Ou seja, o usuário pode apartir de um COMIC, clicar em um CHARACTER e navegar entre os EVENTS sem interrupção! 

## 🛫 O planejamento futuro

* Aprimorar a implementação da arquitetura MVVM
* Introduzir Clean Architecture (useCases, RepositoryImpl e etc)
* Criar mais Activitys para dividir a resposibilidade e reduzir o peso da MainActivity
* Melhorar o UI/UX do aplicativo!

## 📷 Mais imagens

### Fluxo de personagens 

<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/ce54616d-c643-40b7-8aa9-98233faebd99" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/2d74bb55-0b59-4f4b-bd98-987c9df36d97" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/d6f43ba1-6e7a-4eab-8ee0-bc9918628649" width="250"/>

### Fluxo de stories 

<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/c8f5419f-cd40-4837-a6ee-1b3efc269388" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/756b8c20-f03c-450b-8cbe-5639d91274f1" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/8f59587a-50d9-4d6e-bfe9-0cfbb56392eb" width="250"/>

### Fluxo de Movies 

<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/9bff7ae3-f13e-4eed-9387-6318a367b1f0" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/c58be3e9-c906-4c8c-ba49-9713443ee53d" width="250"/>
<img src="https://github.com/alexbr-alves/MarvelExplorer/assets/103543739/4b042a6c-cbf1-4e22-b3cd-e3ec4e1bb984" width="250"/>

