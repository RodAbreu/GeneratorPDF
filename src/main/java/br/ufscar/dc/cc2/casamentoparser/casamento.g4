grammar casamento;

programa:
    titulo
    numConvidados
    data
    orcamento
    listaPadrinhos
    listaPresentes
    listaConvidados
    listaServicos
;

titulo:
    'casamento:' STRING
;

numConvidados:
    'convidados:' NUM_INT
;

data:
    'data:' DATA
;

orcamento:
	'orcamento:' 'R$' NUM_REAL
;

listaPadrinhos:
    'padrinhos:' '['
        padrinho*
    ']'
;

padrinho:
    sigla=NOME ':' completo=STRING
;

listaPresentes:
    'presentes:' '[' 
        presente* 
    ']'
;

presente:
    '{'
        descricao=STRING ','
        url=STRING ','
        '[' (nome+=NOME (',' nome+=NOME)*)? ']'
    '}'
;

listaConvidados:
    'convidados:' '['
        STRING (',' STRING)*
    ']'
;

listaServicos:
    'servicos:' '{'
          'fotografo:' fotografo+
          ('buffet:' buffet+)?
          'cerimonial:' cerimonial+
          'local:' local+
          'musica:' musica+
          'decoracao:' decoracao+
          'convites:' convites+
          'lua_de_mel:' lua_de_mel+
    '}'
;

fotografo:
    '{'
        'nome:' nome=STRING
        'contato:' contato=NUM_INT
        'preco:' 'R$' preco=NUM_REAL
    '}'
;

buffet:
    '{'
        'nome:' STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
    '}'
;

cerimonial:
    '{'
        'nome:' STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
    '}'
;

local:
    '{'
        'nome:' nome=STRING
        'endereco:' endereco=STRING
        'contato:' contato=NUM_INT
        'horario_inicio:' horario_inic=HORARIO
        'horario_fim:' horario_fim=HORARIO
        'preco:' 'R$' NUM_REAL
        'capacidade:' capacidade=NUM_INT
    '}'
;

musica:
    '{'
        'nome:' STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
        'instrumento:' STRING (',' STRING)*
        'musica:' STRING (',' STRING)*
    '}'
;

decoracao:
    '{'
        'nome:' nome=STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
        'itens_decoracao:' item+=STRING (',' item+=STRING)*
    '}'
;

convites:
    '{'
        'nome:' STRING
        'contato:' contato=NUM_INT
        'quantidade_convites:' quant_convites=NUM_INT
        'preco_unidade:' 'R$' NUM_REAL
    '}'
;

lua_de_mel:
    '{'
        'local:' loc=STRING
        'hospedagem:' hospedagem=STRING
        'contato_hospedagem:' NUM_INT
        'preco_total:' 'R$' preco =NUM_REAL
        'data_ida:' data_ida=DATA
        'data_volta:' data_volta=DATA
        'valor_passagem:' 'R$' passagem =NUM_REAL
    '}'
;

NOME: [a-z][a-z0-9]+
;

//STRING: ('a'..'z' | 'A'..'Z' | '_' | ' ' | '\r' | '\n') ('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | ' ' | '\r' | '\n')*
STRING: '"' ~('"'|'\n')* '"'
;

DATA: ('0'..'3') ('0'..'9') '/' ('0'..'1') ('0'..'9') '/' ('0'..'2') ('0'..'9') ('0'..'9') ('0'..'9')
;

HORARIO:
    ('0'..'2')('0'..'9') 'h' ('0'..'5')('0'..'9')
;

NUM_INT: ('0'..'9')+
;

NUM_REAL : ('0' .. '9')+ '.' ('0' .. '9')+
;

WS: (' ' | '\r' | '\n' |'\t')+ -> skip;