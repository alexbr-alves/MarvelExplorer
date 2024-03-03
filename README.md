# Aplicativo Marvel Comics

### Marvel Explorer é o aplicativo Android para todos os fãs do universo Marvel. Com uma interface intuitiva e recursos poderosos, permite aos usuários explorar o vasto mundo dos super-heróis da Marvel de uma maneira envolvente e informativa.

<br>

<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/60194c18-380a-40b3-9541-f8fde67706a0" width="250"/>
<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/0d9bc50b-cf82-40e9-94d0-4203f9071bf2" width="250"/>
<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/d0cf833b-8743-4c4d-91a8-295ea7d4bae7" width="250"/>

<br>

## 🚀 Começando

* Exploração de Personagens: Os usuários podem explorar uma extensa galeria de personagens da Marvel, incluindo heróis, vilões e anti-heróis. Cada personagem vem com uma biografia detalhada, estatísticas de poder e uma lista de histórias em quadrinhos em que aparecem.

* Histórias em Quadrinhos: Acesso a uma biblioteca completa de histórias em quadrinhos da Marvel, permitindo aos usuários lerem suas histórias favoritas direto do aplicativo. Os usuários podem pesquisar por título, personagem ou evento específico.

* Notícias e Atualizações: Fique por dentro das últimas notícias, lançamentos de filmes, séries de TV e eventos relacionados ao universo Marvel. Receba notificações sobre novos lançamentos e atualizações importantes.

* Eventos e Cronologia: Explore eventos importantes do universo Marvel e sua cronologia, permitindo aos usuários entenderem melhor a ordem dos acontecimentos e como as histórias se interligam.

* Feed: Sessão que exibe todos as atualizações diarias da Marvel

* Busca Avançada: Um mecanismo de busca que permite aos usuários encontrar rapidamente personagens, histórias em quadrinhos, eventos e muito mais.

* Integração com o Portal Marvel: O aplicativo utiliza a API oficial da Marvel para obter dados precisos e atualizados sobre personagens, histórias em quadrinhos e outros conteúdos, garantindo uma experiência confiável e autêntica.

### 📋 O que usei!

* Framework: Android
* Linguagem: Kotlin
* Arquitetura: MVVM
* Device: Emulador PIXEL 8 Pro API 30
* Database: SQLite (DAO)
* Injeção de dependência: Hilt

### 🔧 Informações

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

<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/39cb76b3-2c05-49f9-8f2a-d68546878b13" width="250"/>
<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/b05bd448-4f5c-4f30-8fee-ebb8e2d960e7" width="250"/>
<img src="https://github.com/alexbr-alves/Marvel-App-Android/assets/103543739/eafda058-405e-46be-8879-dda2fc3fc733" width="250"/>
