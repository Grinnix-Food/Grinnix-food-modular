# Grinnix Food - Monólito Modular

O Grinnix Food é um sistema interno para gerenciamento de pedidos em restaurantes, desenvolvido com foco na simulação de fluxos reais de compra, processamento de pagamentos e comunicação entre diferentes áreas do negócio. Durante sua evolução, o projeto passou de uma arquitetura monolítica tradicional para uma arquitetura de monólito modular, permitindo uma melhor separação de responsabilidades sem a necessidade imediata de introduzir a complexidade operacional de microsserviços.

## Arquitetura e Fluxo Principal do Sistema

A primeira versão do sistema foi construída seguindo o padrão MVC complementado pelas camadas de Service e Repository. Embora essa abordagem tenha atendido adequadamente às necessidades iniciais do projeto, o crescimento do domínio de pedidos e a introdução de novas funcionalidades tornaram cada vez mais evidente a necessidade de uma organização orientada por contexto de negócio.

Como resultado, a aplicação foi reorganizada em módulos independentes de domínio. Em vez de agrupar controllers, services, repositories e entidades em diretórios compartilhados por responsabilidade técnica, cada domínio passou a possuir sua própria estrutura interna. Dessa forma, os módulos de Order, Payment, Product, Notification e Health passaram a concentrar todos os componentes relacionados às suas respectivas responsabilidades.

Essa mudança aproximou a estrutura do código da própria linguagem do negócio. Funcionalidades relacionadas a pedidos passaram a estar localizadas exclusivamente dentro do módulo Order, enquanto regras de pagamento, notificações e catálogo de produtos permaneceram isoladas em seus respectivos contextos. Como consequência, a navegação pelo código tornou-se mais intuitiva, a manutenção mais simples e a identificação de dependências entre módulos mais clara.

O fluxo principal da aplicação continua iniciando com a criação de um pedido. Após sua criação, produtos podem ser adicionados ao pedido através do módulo Product. Quando o cliente conclui sua seleção, o pedido é finalizado e uma ordem de pagamento é gerada automaticamente pelo módulo Payment.

O processamento do pagamento ocorre através de um endpoint dedicado que simula a integração com um serviço externo. Após a confirmação da transação, o módulo Notification realiza o envio de uma mensagem contendo as informações do pedido para a cozinha. Durante o desenvolvimento, os e-mails são interceptados pelo MailHog, permitindo a visualização completa da comunicação sem a necessidade de um servidor SMTP real.

A adoção dessa arquitetura modular permitiu preservar a simplicidade operacional de uma única aplicação enquanto introduziu uma organização interna próxima daquela encontrada em sistemas distribuídos orientados por domínios.

## Evolução do Desenvolvimento e Ganhos Obtidos com a Modularização

Nesta etapa do projeto, o domínio de negócio já se encontra bem definido e com suas principais regras mapeadas. A consolidação dos requisitos permitiu que a equipe concentrasse seus esforços na organização arquitetural da aplicação, priorizando aspectos como separação de responsabilidades, manutenibilidade e capacidade de evolução do sistema.

O módulo de pedidos permaneceu como o núcleo central da aplicação, concentrando as operações relacionadas ao ciclo de vida de uma compra. Por ser o ponto de integração entre produtos, pagamentos e notificações, sua estrutura passou por um refinamento contínuo para garantir que cada responsabilidade permanecesse em seu respectivo contexto de negócio, evitando o crescimento excessivo do acoplamento entre módulos.

Com uma visão mais madura sobre o funcionamento do sistema e sobre as fronteiras entre os domínios, a reorganização para uma arquitetura modular ocorreu de forma natural. A clareza das responsabilidades de cada módulo simplificou decisões técnicas e acelerou o desenvolvimento.


Um dos experimentos realizados durante o desenvolvimento consistiu na substituição da implementação interna do módulo de pagamentos sem realizar alterações nos demais módulos da aplicação. O objetivo era verificar se a separação arquitetural proposta realmente permitia a evolução isolada de um domínio sem gerar impactos significativos no restante do sistema. 

O resultado demonstrou que a mudança foi realizada com baixo esforço, concentrando-se exclusivamente nos componentes internos do módulo Payment. Nenhuma modificação foi necessária nos módulos de pedidos, produtos ou notificações, evidenciando que as dependências entre os domínios estão definidas por contratos e responsabilidades bem delimitadas. Esse comportamento reforça um dos principais benefícios da arquitetura de monólito modular: embora todos os módulos compartilhem o mesmo processo de execução, cada domínio pode evoluir de forma relativamente independente, como se estivesse em seu próprio contexto. 

Na prática, isso permite que implementações internas sejam substituídas, refatoradas ou expandidas sem provocar alterações em cascata pelo restante da aplicação, reduzindo o custo de manutenção e aumentando a capacidade de evolução do sistema ao longo do tempo.

Esses ganho de organização não apenas acelerou o desenvolvimento de novas funcionalidades, mas também tornou o processo de manutenção mais previsível e seguro.

## Desafios Atuais da Arquitetura

Embora a modularização tenha reduzido significativamente o acoplamento estrutural da aplicação, algumas dependências continuam existindo devido à natureza do fluxo de negócio.

O módulo de pedidos permanece como o principal orquestrador da aplicação. Ele depende diretamente do catálogo de produtos para composição dos pedidos e do módulo de pagamentos para conclusão das transações. Após a confirmação do pagamento, também existe uma dependência indireta em relação ao módulo de notificações.

Essa característica não representa necessariamente um problema. Pelo contrário, ela reflete a realidade do domínio de negócio. Entretanto, exige atenção para evitar que responsabilidades pertencentes a outros contextos sejam gradualmente absorvidas pelo módulo de pedidos ao longo da evolução do sistema.

Outro desafio futuro está relacionado à consistência transacional entre os módulos. Como todos ainda compartilham o mesmo banco de dados e o mesmo processo de execução, determinadas operações continuam fortemente acopladas do ponto de vista de persistência.

## Possíveis Estratégias Futuras de Extração para Serviços Independentes

A atual organização modular permite visualizar com maior clareza quais domínios possuem potencial para se tornarem serviços independentes caso a aplicação venha a crescer.

O módulo Payment apresenta o maior potencial para extração. Sua responsabilidade é bem definida, possui regras próprias, ciclo de vida próprio e tende naturalmente a crescer com a introdução de múltiplos meios de pagamento, gateways externos, reembolsos, estornos e auditorias financeiras. Seu nível de coesão interna é elevado e suas dependências com os demais módulos são relativamente simples.

O módulo Notification também é um forte candidato. O envio de notificações geralmente evolui para múltiplos canais, como e-mail, SMS, push notifications e integrações com sistemas externos. Como sua responsabilidade é altamente especializada, a separação futura tende a ser natural.

O módulo Order possui enorme relevância para o negócio, porém sua extração exige maior cuidado. Apesar de possuir alta coesão interna, ele atua como ponto central de integração entre diversos fluxos da aplicação. Em um cenário distribuído, sua separação demandaria mecanismos adicionais de comunicação assíncrona, eventos e estratégias de consistência entre serviços.

O módulo Product apresenta uma situação intermediária. Em aplicações de pequeno e médio porte, ele pode permanecer integrado ao domínio de pedidos sem gerar problemas significativos. Entretanto, caso o catálogo cresça consideravelmente, com categorias, promoções, busca avançada e gestão de estoque, sua separação poderá se tornar vantajosa.

Por outro lado, o módulo Health não possui justificativa prática para ser transformado em um serviço independente. Sua responsabilidade é exclusivamente operacional e está diretamente associada à observabilidade da aplicação principal.

Dessa forma, sob a perspectiva de coesão e responsabilidade de domínio, os módulos Payment e Notification representam os candidatos mais naturais para uma futura migração para microsserviços, enquanto Product pode ou não seguir esse caminho dependendo do crescimento do catálogo. Já o domínio de Order tende a permanecer como o núcleo central do negócio, exigindo uma análise arquitetural mais cuidadosa antes de qualquer processo de extração.


# Como rodar

O projeto foi planejado para usar a sua infra-estrutura com containers docker, onde o docker compose irá orquestrar para você tanto o banco de dados como o serviço de email ( mailhog ) que captura cada envio de email. O docker compose desse repositório também é responsável por realizar o build da aplicação e executa-la em um container, então para subir toda a infraestrutura e buildar e executar a aplicação do grinnix-food basta executar o seguinte comando:

```
  docker compose up -d --build -> versão sem a cli do docker compose instalada
  docker-compose up -d --build -> versão com a cli do docker compose
```