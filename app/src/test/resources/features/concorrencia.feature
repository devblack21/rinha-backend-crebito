# language: pt
# encoding: utf-8
@Transaction @Concorrencia
Funcionalidade: Concorrencia de Transações

  Esquema do Cenario: Cadastro concorrente de Transação
    Dado que tenho um cliente com saldo de <saldo> e limite de <limite>
    E envio uma lista de transações
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 50  | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 50  | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 100 | d |
      | 50  | d |
      | 100 | d |
      | 100 | d |
      | 50  | d |
      | 50  | d |
      | 50  | d |
    Entao consulto o extrato do cliente
    E valido no extrato se o saldo do cliente é igual a <saldoEsperado>
    Exemplos:
      | saldo | limite | saldoEsperado |
      | 0     | 1000   | -1000         |
      | 0     | 50     | -50           |
      | 5     | 50     | -45           |
      | 150   | 50     | -50           |
      | 200   | 1000   | -1000         |
      | 0     | 1055   | -1050         |
      | 500   | 522    | -500          |