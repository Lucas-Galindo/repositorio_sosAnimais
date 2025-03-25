#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
// #include <windows.h> // Incluir se for necess�rio

//|-------------|  [STRUCTS]  |--------------|
struct status
{
	char status;
	struct status *prox;
};
typedef struct status Status;

union typeDado
{
	float valorN;
	char valorL;
	char valorD[11];
	char valorC[50];
	char valorM[50];
};
typedef union typeDado TypeDado;

struct dados
{
	union typeDado dado;
	struct dados *prox;
};
typedef struct dados Dados;

struct campo
{
	char fieldName[15];
	char type;
	int width, dec;
	struct dados *pDados;
	struct campo *pAtual, *prox;
};
typedef struct campo Campo;

struct arquivo
{
	char nomeDbf[20], data[11], hora[6];
	struct status *status;
	struct campo *campos;
	struct arquivo *prox, *ant;
};
typedef struct arquivo Arquivo;

struct unidade
{
	struct arquivo *arqs;
	struct unidade *bottom, *top;
	char unidade[2];
};
typedef struct unidade Unidade;

// STRUCT DE COMANDOS:
struct tokenCmd
{
	char token_name[15];
	struct tokenCmd *prox;
};
typedef struct tokenCmd TokenCmd;

//|------|  [DECLARACOES DE FUNCOES]  |-------|
void inserirUnidade(Unidade **inicio, char unidade[]);
void lerString(char *str, int max);
void liberarTokens(TokenCmd *tokens);
TokenCmd *dividirComando(char *comando);
void ativarComando(Unidade *unidade, Unidade **unidadeAtual, char comando[]);
void lerComandos();
void setDefault(Unidade *unidade, Unidade **unidadeAtual, char unidadeName[]);

//|-------|  [MANIPULAR UNIDADE]  |------------|
void inserirUnidade(Unidade **inicio, char unidade[])
{
	Unidade *unid, *aux;

	unid = (Unidade *)malloc(sizeof(Unidade));
	if (!unid)
	{
		printf("Erro de alocacao de memoria.\n");
		exit(1);
	}

	strcpy(unid->unidade, unidade);

	unid->arqs = NULL;
	unid->bottom = NULL;
	unid->top = NULL;

	if (*inicio == NULL)
		*inicio = unid;
	else
	{
		aux = *inicio;
		while (aux->bottom != NULL)
			aux = aux->bottom;
		aux->bottom = unid;
		unid->top = aux;
	}
}

//|---------|  [FUNCOES EXTRAS]  |-------------|
void lerString(char *str, int max)
{
	int i = 0;
	char c;
	while (i < max - 1 && (c = getchar()) != '\n' && c != EOF)
	{
		str[i++] = c;
	}
	str[i] = '\0';

	if (c != '\n' && c != EOF)
	{
		while (getchar() != '\n')
			;
	}

	for (i = 0; str[i] != '\0'; i++)
	{
		str[i] = toupper((unsigned char)str[i]);
	}
}

void liberarTokens(TokenCmd *tokens)
{
	TokenCmd *aux;
	while (tokens != NULL)
	{
		aux = tokens;
		tokens = tokens->prox;
		free(aux);
	}
}

//|-----------|  [LER COMANDOS]  |-------------|
TokenCmd *dividirComando(char *comando)
{
	TokenCmd *token = NULL, *aux, *nova = NULL;
	int i = 0, j = 0;
	int tam = strlen(comando);
	char letra, token_temp[15];

	while (i <= tam)
	{
		letra = comando[i];
		if (letra == ' ' || letra == '\0')
		{
			if (j > 0)
			{
				token_temp[j] = '\0';
				nova = (TokenCmd *)malloc(sizeof(TokenCmd));
				if (!nova)
				{
					printf("Erro de alocacao de memoria.\n");
					exit(1);
				}
				strcpy(nova->token_name, token_temp);
				nova->prox = NULL;

				if (token == NULL)
					token = nova;
				else
				{
					aux = token;
					while (aux->prox != NULL)
						aux = aux->prox;
					aux->prox = nova;
				}
				j = 0;
			}
			i++;
		}
		else
		{
			token_temp[j++] = letra;
			i++;
		}
	}
	return token;
}

void ativarComando(Unidade *unidade, Unidade **unidadeAtual, char comando[])
{
	TokenCmd *tokens = dividirComando(comando);
	TokenCmd *aux = tokens;
	if (!tokens)
		return;

	if (aux && strcmp(aux->token_name, "SET") == 0 && (aux = aux->prox) && strcmp(aux->token_name, "DEFAULT") == 0 && (aux = aux->prox) && strcmp(aux->token_name, "TO") == 0 && (aux = aux->prox))
		setDefault(unidade, &unidadeAtual, aux->token_name);

	if (aux && strcmp(aux->token_name, "CREATE") == 0 && (aux = aux->prox))
	{
		createDBF(aux->token_name, &unidadeAtual);
	}

	// Exemplo de comando DIR
	if (aux && strcmp(aux->token_name, "DIR") == 0)
	{
		printf("\n\nDIR\n\n");
		// Exemplo: dir(&unidade_atual);
	}

	// Exemplo de comando USE
	if (aux && strcmp(aux->token_name, "USE") == 0 && (aux = aux->prox))
	{
		printf("\n\nUSE\n\n");
		// Exemplo: use_dbf(aux->token_name, &unidade_atual);
	}

	liberarTokens(tokens);
}

//|-------------|  [COMANDOS]  |---------------|
void setDefault(Unidade *unidade, Unidade **unidadeAtual, char unidadeName[])
{
	*unidadeAtual = unidade;

	while (*unidadeAtual != NULL && strcmp((*unidadeAtual)->unidade, unidadeName) != 0)
		*unidadeAtual = (*unidadeAtual)->bottom;

	if (*unidadeAtual == NULL)
		printf("\nUnidade [%s] não encontrada.\n", unidadeName);
	else
		printf("\nUnidade definida para [%s]\n", (*unidadeAtual)->unidade);
}

void createDBF(char nomeArquivo[], Unidade **unidadeAtual)
{
	char continuar;
	printf("\n [ESC] - Sair");
	do
	{
		//verificar .dbf
		printf("\n nome: %s", nomeArquivo);
		continuar = getch();
	} while (continuar != 27);
}

//|---------------|  [MAIN]  |-----------------|
int main(void)
{
	// Inserindo Unidades:
	Unidade *unidade = NULL;
	inserirUnidade(&unidade, "C:");
	inserirUnidade(&unidade, "D:");
	Unidade *unidadeAtual = unidade;

	// Ler Comandos:
	char comando[30];
	do
	{
		printf("\nDigite o comando: ");
		lerString(comando, sizeof(comando));
		ativarComando(unidade, &unidadeAtual, comando);
	} while (strcmp(comando, "QUIT") != 0);

	return 0;
}