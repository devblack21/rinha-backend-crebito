# language: pt
# encoding: utf-8
@Transaction
Funcionalidade: Cadastro de transações

  Esquema do Cenario: Cadastro de Transação de débito realizada
    Dado que tenho um cliente com saldo de <saldo> e limite de <limite>
    E a descricao for "debito"
    E envio um cadastro de transação de "débito" no valor de <valor>
    Entao valido se a transação foi realizada
    E consulto o extrato do cliente
    E valido que o status code da transacao é igual a 200
    E valido a conformidade dos dados no extrato
    E valido no extrato se o saldo do cliente é igual a <saldoEsperado>
    Exemplos:
      | saldo | limite | valor | saldoEsperado |
      | 0     | 100    | 50    | -50           |
      | 500   | 100    | 500   | 0             |
      | -10   | 100    | 90    | -100          |
      | 10    | 155    | 160   | -150          |

  Esquema do Cenario: Cadastro de Transação de débito não realizada
    Dado que tenho um cliente com saldo de <saldo> e limite de <limite>
    E a descricao for "debito"
    E envio um cadastro de transação de "débito" no valor de <valor>
    Entao valido que a transação não foi realizada
    E consulto o extrato do cliente
    E valido que o status code da transacao é igual a 422
    E valido que não existe a transação cadastrada no extrato
    E valido no extrato se o saldo do cliente é igual a <saldoEsperado>
    Exemplos:
      | saldo | limite | valor | saldoEsperado |
      | 0     | 100    | 150   | 0             |
      | 500   | 100    | 601   | 500           |
      | -10   | 100    | 91    | -10           |
      | 10    | 155    | 166   | 10            |

  Esquema do Cenario: Cadastro de Transação de crédito realizada
    Dado que tenho um cliente com saldo de <saldo> e limite de <limite>
    E a descricao for "crédito"
    E envio um cadastro de transação de "crédito" no valor de <valor>
    Entao valido se a transação foi realizada
    E consulto o extrato do cliente
    E valido que o status code da transacao é igual a 200
    E valido a conformidade dos dados no extrato
    E valido no extrato se o saldo do cliente é igual a <saldoEsperado>
    Exemplos:
      | saldo | limite | valor | saldoEsperado |
      | 0     | 100    | 50    | 50            |
      | 500   | 100    | 700   | 1200          |
      | -10   | 100    | 100   | 90            |
      | 100   | 155    | 300   | 400           |


  Cenario: Cadastrar Transação para cliente inexistente
    Dado que não tenho nenhum cliente cadastrado
    E a descricao for "crédito"
    E envio um cadastro de transação de "crédito" no valor de 1000
    Entao valido que a transação não foi realizada
    E valido que o status code da transacao é igual a 404
    E consulto o extrato do cliente
    E valido que o status code da consulta é igual a 404


  Cenario: Consultar Extrato para cliente inexistente
    Dado que não tenho nenhum cliente cadastrado
    E consulto o extrato do cliente
    Entao valido que o status code da consulta é igual a 404