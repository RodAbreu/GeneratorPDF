grammar casamento;

programa:
    titulo
    numConvidados
    data
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

listaPadrinhos:
    'padrinhos:' '['
        padrinho*
    ']'
;

padrinho:
    NOME ':' STRING
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
        '[' (NOME (',' NOME)*)? ']'
    '}'
;

listaConvidados:
    'convidados:' '['
        STRING (',' STRING)*
    ']'
;

listaServicos:
    'servicos:' '['
          'fotografo:' fotografo
        | 'buffet:' buffet
        | 'cerimonial:' cerimonial
        | 'local:' local
        | 'musica:' musica
        | 'decoracao:' decoracao
        | 'convites:' convites
        | 'lua_de_mel:' lua_de_mel
    ']'
;

fotografo:
    '{' 
        'nome:' STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
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
        'nome:' STRING
        'endereco:' STRING
        'contato:' NUM_INT
        'horario_inicio:' HORARIO
        'horario_fim:' HORARIO
        'preco:' 'R$' NUM_REAL
        'capacidade:' NUM_INT
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
        'nome:' STRING
        'contato:' NUM_INT
        'preco:' 'R$' NUM_REAL
        ('itens_decoracao:' STRING)?
    '}'
;

convites:
    '{'
        'nome:' STRING
        'contato:' NUM_INT
        'quantidade_convites:' NUM_INT
        'preco_unidade:' 'R$' NUM_REAL
    '}'
;

lua_de_mel:
    '{'
        'local:' STRING
        'hospedagem:' STRING
        'contato_hospedagem:' NUM_INT
        'preco_total:' 'R$' NUM_REAL
        'data_ida:' DATA
        'data_volta:' DATA
        'valor_passagem:' 'R$' NUM_REAL
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

NUM_REAL : ('0' .. '9')+ ',' ('0' .. '9')+
;

WS: (' ' | '\r' | '\n' |'\t')+ -> skip;