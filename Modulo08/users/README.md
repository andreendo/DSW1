# Users - aplicação básica para ilustrar autenticação e autorização

* Esta aplicação utiliza um banco de dados H2 (configuração simplificada)
  * Veja application.properties para mais detalhes

## Entidades

* User - usuário, password, e papel (perfil de acesso)
* Item - um item pertence a 1 e somente 1 usuário

## Regras de Negócio

1. Temos 2 perfis de acesso: ADMIN e USER
2. A rota `/login` é pública
3. As rotas `/admin/**` demanda autenticação e o perfil `ADMIN`
4. As rotas `/user/**` demanda autenticação e o perfil `USER`
5. As demais rotas, e.g. `\item\**`, demandam apenas autenticação. Em outras palavras, os 2 perfis podem acessar estas rotas. 
6. Ambos perfis pode adicionar um Item (sendo o usuário autenticado seu dono). No caso de sucesso, o controlador redireciona para a tela principal de cada perfil. Classe `SaveItemController`.
7. A lista de Items apresentada na tela principal depende de cada perfil:
   * USER: Mostrar apenas os Items cujo usuário logado é dono. Classe `UserController`. 
   * ADMIN: Mostrar todos os Items que existe no BD. Classe `AdminController`.
8. A remoção de um Item segue as seguintes restrições, classe.método `ItemService.deleteItem(..)`:
   * O dono do Item sempre pode excluir.
   * Usuários com perfil ADMIN pode excluir Items de qualquer usuário com perfil USER. 