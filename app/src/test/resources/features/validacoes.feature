# language: pt
# encoding: utf-8
@Transaction @Validacao
Funcionalidade: Validação de campos

  Esquema do Cenario: Validação do campo tipo
    Dado que tenho um cliente com saldo de 0 e limite de 100
    E envio um cadastro de transação de "<tipo>" no valor de 10
    Entao valido que a transação não foi realizada
    E valido que o status code da transacao é igual a 400
    Exemplos:
      | tipo     |
      | XBacon   |
      | Abobora  |
      | i        |
      | t        |
      | tatu     |
      | 1        |
      | 40       |
      | Creddito |
      | Deebitoo |
      | null     |

  Esquema do Cenario: Validação do campo valor
    Dado que tenho um cliente com saldo de 0 e limite de 100
    E envio um cadastro de transação de "Débito" no valor de <valor>
    Entao valido que a transação não foi realizada
    E valido que o status code da transacao é igual a 400
    Exemplos:
      | valor  |
      | 0      |
      | -1     |
      | -500   |
      | -800   |
      | -10    |
      | -2     |
      | -15874 |
      | null   |

  Esquema do Cenario: Validação do campo descricao
    Dado que tenho um cliente com saldo de 0 e limite de 100
    Quando a descricao for "<descricao>"
    E envio um cadastro de transação de "d" no valor de 10
    Entao valido que a transação não foi realizada
    E valido que o status code da transacao é igual a 400
    Exemplos:
      | descricao                     |
      | aaaaaaaaaaa                   |
      | bbbbbbbbbbb                   |
      | cccccccccccc                  |
      | ddddddddddddddddddddddddddddd |
      | null                          |