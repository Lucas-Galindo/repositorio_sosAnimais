<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simulação - Listagem de Transferências</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .page-title {
            color: #2c3e50;
            margin-bottom: 30px;
            font-size: 2rem;
            display: flex;
            align-items: center;
        }

        .page-title i {
            margin-right: 10px;
            color: #e67e22;
        }

        .modo-dev-badge {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 0.9rem;
            margin-left: 20px;
            font-weight: bold;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { box-shadow: 0 0 0 0 rgba(255, 107, 107, 0.7); }
            70% { box-shadow: 0 0 0 10px rgba(255, 107, 107, 0); }
            100% { box-shadow: 0 0 0 0 rgba(255, 107, 107, 0); }
        }

        .filtros-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
            overflow: hidden;
        }

        .filtros-header {
            background: linear-gradient(135deg, #e67e22, #d35400);
            color: white;
            padding: 20px;
        }

        .filtros-titulo {
            font-size: 1.3rem;
            font-weight: bold;
        }

        .filtros-body {
            padding: 25px;
        }

        .filtros-linha {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px;
            margin-bottom: 25px;
        }

        .grupo-filtro label {
            font-weight: 600;
            margin-bottom: 8px;
            color: #2c3e50;
            display: block;
        }

        .grupo-filtro input, .grupo-filtro select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 0.95rem;
            transition: border-color 0.3s;
        }

        .grupo-filtro input:focus, .grupo-filtro select:focus {
            outline: none;
            border-color: #e67e22;
            box-shadow: 0 0 0 3px rgba(230, 126, 34, 0.2);
        }

        .acoes-filtro {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: all 0.3s;
        }

        .btn-pesquisar {
            background: linear-gradient(135deg, #3498db, #2980b9);
            color: white;
        }

        .btn-listar-todas {
            background: linear-gradient(135deg, #e67e22, #d35400);
            color: white;
        }

        .btn-limpar-filtros {
            background: linear-gradient(135deg, #95a5a6, #7f8c8d);
            color: white;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .resultados-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .resultados-header {
            background: linear-gradient(135deg, #e67e22, #d35400);
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .resultados-titulo {
            font-size: 1.3rem;
            font-weight: bold;
        }

        .contador-resultados {
            background: rgba(255, 255, 255, 0.2);
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 0.9rem;
        }

        .tabela-resultados {
            width: 100%;
            border-collapse: collapse;
        }

        .tabela-resultados th {
            background: #f8f9fa;
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #2c3e50;
            border-bottom: 2px solid #e9ecef;
        }

        .tabela-resultados td {
            padding: 15px;
            border-bottom: 1px solid #e9ecef;
            vertical-align: middle;
        }

        .linha-transferencia {
            cursor: pointer;
            transition: all 0.3s;
        }

        .linha-transferencia:hover {
            background: linear-gradient(90deg, #f8f9fa, #e3f2fd);
            transform: translateX(5px);
        }

        .linha-transferencia.expandida {
            background: linear-gradient(90deg, #e3f2fd, #bbdefb);
            border-left: 4px solid #e67e22;
        }

        .detalhes-transferencia {
            background: #f8f9fa;
            border-left: 4px solid #e67e22;
        }

        .detalhes-conteudo {
            padding: 25px;
            background: linear-gradient(135deg, #f8f9fa, #e3f2fd);
        }

        .detalhes-titulo {
            font-weight: bold;
            color: #e67e22;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 1.1rem;
        }

        .tabela-detalhes {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .tabela-detalhes th {
            background: #2c3e50;
            color: white;
            padding: 12px;
            font-weight: 600;
            width: 120px;
        }

        .tabela-detalhes td {
            padding: 12px;
            border-bottom: 1px solid #e9ecef;
        }

        .badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 0.8rem;
            font-weight: 600;
            margin-left: 8px;
        }

        .badge-comum {
            background: #27ae60;
            color: white;
        }

        .badge-medica {
            background: #f39c12;
            color: white;
        }

        .icone-expandir {
            color: #e67e22;
            transition: transform 0.3s;
            margin-right: 8px;
        }

        .icone-expandir.expandido {
            transform: rotate(90deg);
        }

        .toast {
            position: fixed;
            top: 20px;
            right: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            padding: 16px 20px;
            display: flex;
            align-items: center;
            max-width: 400px;
            z-index: 1000;
            animation: slideIn 0.3s ease-out;
        }

        @keyframes slideIn {
            from { transform: translateX(100%); }
            to { transform: translateX(0); }
        }

        .toast-info {
            border-left: 4px solid #3498db;
        }

        .toast-info .icone-toast {
            color: #3498db;
            margin-right: 12px;
            font-size: 1.2rem;
        }

        .titulo-toast {
            font-weight: 600;
            margin-bottom: 4px;
        }

        .mensagem-toast {
            color: #666;
            font-size: 0.9rem;
        }

        .highlight {
            background: yellow;
            padding: 2px 4px;
            border-radius: 3px;
            animation: highlight 2s ease-out;
        }

        @keyframes highlight {
            from { background: yellow; }
            to { background: transparent; }
        }

        .exemplo-uso {
            background: linear-gradient(135deg, #74b9ff, #0984e3);
            color: white;
            padding: 20px;
            border-radius: 12px;
            margin: 30px 0;
        }

        .exemplo-uso h3 {
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .exemplo-lista {
            list-style: none;
            padding-left: 0;
        }

        .exemplo-lista li {
            margin: 8px 0;
            padding-left: 20px;
            position: relative;
        }

        .exemplo-lista li:before {
            content: '✓';
            position: absolute;
            left: 0;
            color: #00b894;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="page-title">
            <i class="fas fa-exchange-alt"></i>
            Listagem de Transferências
            <span class="modo-dev-badge">
                <i class="fas fa-code"></i>
                MODO DESENVOLVIMENTO
            </span>
        </h1>

        <!-- Toast de Boas-vindas -->
        <div class="toast toast-info">
            <i class="fas fa-info-circle icone-toast"></i>
            <div>
                <div class="titulo-toast">Sistema de Transferências - DESENVOLVIMENTO (dados simulados)</div>
                <div class="mensagem-toast">Use os filtros para pesquisar transferências ou clique em "Listar Todas"</div>
            </div>
        </div>

        <!-- Filtros -->
        <div class="filtros-container">
            <div class="filtros-header">
                <div class="filtros-titulo">Filtros de Pesquisa</div>
            </div>
            <div class="filtros-body">
                <div class="filtros-linha">
                    <div class="grupo-filtro">
                        <label>Data Inicial:</label>
                        <input type="date" value="2025-05-23">
                    </div>
                    <div class="grupo-filtro">
                        <label>Data Final:</label>
                        <input type="date" value="2025-05-27">
                    </div>
                    <div class="grupo-filtro">
                        <label>Matrícula do Funcionário:</label>
                        <input type="number" placeholder="Digite a matrícula">
                    </div>
                    <div class="grupo-filtro">
                        <label>Nome do Funcionário:</label>
                        <input type="text" placeholder="Digite o nome" value="João">
                    </div>
                    <div class="grupo-filtro">
                        <label>Categoria das Baias:</label>
                        <select>
                            <option value="">Todas as categorias</option>
                            <option value="comum">Comum</option>
                            <option value="medica" selected>Médica</option>
                        </select>
                    </div>
                </div>
                <div class="acoes-filtro">
                    <button class="btn btn-pesquisar">
                        <i class="fas fa-search"></i>
                        Pesquisar
                    </button>
                    <button class="btn btn-listar-todas">
                        <i class="fas fa-list"></i>
                        Listar Todas
                    </button>
                    <button class="btn btn-limpar-filtros">
                        <i class="fas fa-times"></i>
                        Limpar Filtros
                    </button>
                </div>
            </div>
        </div>

        <!-- Resultados -->
        <div class="resultados-container">
            <div class="resultados-header">
                <div class="resultados-titulo">Resultados da Pesquisa</div>
                <div class="contador-resultados">3 transferências encontradas</div>
            </div>
            
            <table class="tabela-resultados">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Funcionário</th>
                        <th>Matrícula</th>
                        <th>Detalhes</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Transferência 1 - EXPANDIDA -->
                    <tr class="linha-transferencia expandida">
                        <td><strong>#1</strong></td>
                        <td>27/05/25 10:30</td>
                        <td>João Silva</td>
                        <td><span class="badge badge-comum">15</span></td>
                        <td style="cursor: pointer; color: #e67e22;">
                            <i class="fas fa-chevron-right icone-expandir expandido"></i>
                            <span style="margin-left: 8px;">Clique para ver detalhes</span>
                        </td>
                    </tr>
                    <!-- Detalhes da Transferência 1 -->
                    <tr class="detalhes-transferencia">
                        <td colspan="5">
                            <div class="detalhes-conteudo">
                                <div class="detalhes-titulo">
                                    <i class="fas fa-info-circle"></i>
                                    Detalhes da Movimentação
                                </div>
                                <table class="tabela-detalhes">
                                    <tbody>
                                        <tr>
                                            <th>Animal:</th>
                                            <td><strong>Rex</strong> (Labrador)</td>
                                        </tr>
                                        <tr>
                                            <th>Baia Origem:</th>
                                            <td>Baia Comum 02 <span class="badge badge-comum">comum</span></td>
                                        </tr>
                                        <tr>
                                            <th>Baia Destino:</th>
                                            <td>Baia Médica 01 <span class="badge badge-medica">medica</span></td>
                                        </tr>
                                        <tr>
                                            <th>Tipo de Movimentação:</th>
                                            <td>
                                                <span style="color: #dc3545;">
                                                    <i class="fas fa-ambulance"></i> 
                                                    Transferência para Tratamento
                                                </span>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>

                    <!-- Transferência 2 -->
                    <tr class="linha-transferencia">
                        <td><strong>#2</strong></td>
                        <td>26/05/25 14:15</td>
                        <td>Maria Oliveira</td>
                        <td><span class="badge badge-comum">12</span></td>
                        <td style="cursor: pointer; color: #e67e22;">
                            <i class="fas fa-chevron-right icone-expandir"></i>
                            <span style="margin-left: 8px;">Clique para ver detalhes</span>
                        </td>
                    </tr>

                    <!-- Transferência 3 -->
                    <tr class="linha-transferencia">
                        <td><strong>#5</strong></td>
                        <td>23/05/25 11:10</td>
                        <td>Maria Oliveira</td>
                        <td><span class="badge badge-comum">12</span></td>
                        <td style="cursor: pointer; color: #e67e22;">
                            <i class="fas fa-chevron-right icone-expandir"></i>
                            <span style="margin-left: 8px;">Clique para ver detalhes</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Exemplo de Uso -->
        <div class="exemplo-uso">
            <h3>
                <i class="fas fa-lightbulb"></i>
                Como os Dados Funcionam na Tela:
            </h3>
            <ul class="exemplo-lista">
                <li><strong>Modo Desenvolvimento Ativo:</strong> Sistema usa dados simulados (não precisa do backend)</li>
                <li><strong>Filtros Funcionais:</strong> Pode filtrar por data, funcionário, categoria das baias</li>
                <li><strong>Expansão de Detalhes:</strong> Clique em qualquer linha para ver detalhes da transferência</li>
                <li><strong>Dados Realistas:</strong> Rex foi transferido da Baia Comum 02 para Baia Médica 01</li>
                <li><strong>Validações:</strong> Data inicial não pode ser maior que final</li>
                <li><strong>Toasts Informativos:</strong> Sistema mostra notificações para cada ação</li>
                <li><strong>Interface Responsiva:</strong> Funciona em desktop, tablet e mobile</li>
                <li><strong>Animações Suaves:</strong> Expansão/recolhimento com transições</li>
            </ul>
        </div>

        <div style="text-align: center; margin-top: 40px; padding: 20px; background: #ecf0f1; border-radius: 8px;">
            <h4 style="color: #2c3e50; margin-bottom: 10px;">
                <i class="fas fa-toggle-on"></i>
                Para usar dados reais, altere CONFIG.MODO_DESENVOLVIMENTO para false
            </h4>
            <p style="color: #7f8c8d;">
                Quando conectado ao backend, os dados virão das APIs reais do sistema
            </p>
        </div>
    </div>

    <script>
        // Simular interatividade básica
        document.addEventListener('DOMContentLoaded', function() {
            // Simular clique na transferência não expandida
            const linhasTransferencia = document.querySelectorAll('.linha-transferencia:not(.expandida)');
            
            linhasTransferencia.forEach((linha, index) => {
                linha.addEventListener('click', function() {
                    // Simular toast de carregamento
                    const toast = document.createElement('div');
                    toast.className = 'toast toast-info';
                    toast.innerHTML = `
                        <i class="fas fa-spinner fa-spin icone-toast"></i>
                        <div>
                            <div class="titulo-toast">Carregando</div>
                            <div class="mensagem-toast">Buscando detalhes da transferência...</div>
                        </div>
                    `;
                    
                    document.body.appendChild(toast);
                    
                    setTimeout(() => {
                        toast.remove();
                        
                        // Simular expansão
                        linha.classList.add('expandida');
                        const icone = linha.querySelector('.icone-expandir');
                        icone.classList.add('expandido');
                        
                        // Criar detalhes simulados
                        const detalhes = document.createElement('tr');
                        detalhes.className = 'detalhes-transferencia';
                        
                        const exemplos = [
                            {
                                animal: 'Luna',
                                raca: 'Pastor Alemão',
                                origem: 'Baia Comum 02',
                                destino: 'Baia Médica 01',
                                tipo: 'Transferência para Tratamento'
                            },
                            {
                                animal: 'Rocky',
                                raca: 'Rottweiler', 
                                origem: 'Baia Comum 03',
                                destino: 'Baia Médica 03',
                                tipo: 'Transferência para Tratamento'
                            }
                        ];
                        
                        const exemplo = exemplos[index] || exemplos[0];
                        
                        detalhes.innerHTML = `
                            <td colspan="5">
                                <div class="detalhes-conteudo">
                                    <div class="detalhes-titulo">
                                        <i class="fas fa-info-circle"></i>
                                        Detalhes da Movimentação
                                    </div>
                                    <table class="tabela-detalhes">
                                        <tbody>
                                            <tr>
                                                <th>Animal:</th>
                                                <td><strong>${exemplo.animal}</strong> (${exemplo.raca})</td>
                                            </tr>
                                            <tr>
                                                <th>Baia Origem:</th>
                                                <td>${exemplo.origem} <span class="badge badge-comum">comum</span></td>
                                            </tr>
                                            <tr>
                                                <th>Baia Destino:</th>
                                                <td>${exemplo.destino} <span class="badge badge-medica">medica</span></td>
                                            </tr>
                                            <tr>
                                                <th>Tipo de Movimentão:</th>
                                                <td>
                                                    <span style="color: #dc3545;">
                                                        <i class="fas fa-ambulance"></i> 
                                                        ${exemplo.tipo}
                                                    </span>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        `;
                        
                        linha.insertAdjacentElement('afterend', detalhes);
                        
                        // Toast de sucesso
                        const toastSucesso = document.createElement('div');
                        toastSucesso.className = 'toast toast-info';
                        toastSucesso.innerHTML = `
                            <i class="fas fa-check-circle icone-toast" style="color: #27ae60;"></i>
                            <div>
                                <div class="titulo-toast">Detalhes Carregados</div>
                                <div class="mensagem-toast">Informações da transferência exibidas com sucesso</div>
                            </div>
                        `;
                        
                        document.body.appendChild(toastSucesso);
                        
                        setTimeout(() => {
                            toastSucesso.remove();
                        }, 3000);
                        
                    }, 1000);
                });
            });
            
            // Remover toast inicial após 5 segundos
            setTimeout(() => {
                const toastInicial = document.querySelector('.toast');
                if (toastInicial) {
                    toastInicial.style.animation = 'slideIn 0.3s ease-out reverse';
                    setTimeout(() => toastInicial.remove(), 300);
                }
            }, 5000);
        });
    </script>
</body>
</html>