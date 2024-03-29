package com.jvrskt.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jvrskt.cursomc.domain.Categoria;
import com.jvrskt.cursomc.domain.Cidade;
import com.jvrskt.cursomc.domain.Cliente;
import com.jvrskt.cursomc.domain.Endereco;
import com.jvrskt.cursomc.domain.Estado;
import com.jvrskt.cursomc.domain.ItemPedido;
import com.jvrskt.cursomc.domain.Pagamento;
import com.jvrskt.cursomc.domain.PagamentoBoleto;
import com.jvrskt.cursomc.domain.PagamentoCartao;
import com.jvrskt.cursomc.domain.Pedido;
import com.jvrskt.cursomc.domain.Produto;
import com.jvrskt.cursomc.domain.enums.EstadoPagamento;
import com.jvrskt.cursomc.domain.enums.TipoCliente;
import com.jvrskt.cursomc.repository.CategoriaRepository;
import com.jvrskt.cursomc.repository.CidadeRepository;
import com.jvrskt.cursomc.repository.ClienteRepository;
import com.jvrskt.cursomc.repository.EnderecoRepository;
import com.jvrskt.cursomc.repository.EstadoRepository;
import com.jvrskt.cursomc.repository.ItemPedidoRepository;
import com.jvrskt.cursomc.repository.PagamentoRepository;
import com.jvrskt.cursomc.repository.PedidoRepository;
import com.jvrskt.cursomc.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	public CategoriaRepository catRepo;
	
	@Autowired
	public ProdutoRepository prodRepo;
	
	@Autowired
	public CidadeRepository cidadeRepo;
	
	@Autowired
	public EstadoRepository estadoRepo;
	
	@Autowired
	public EnderecoRepository endRepo;
	
	@Autowired
	public ClienteRepository cliRepo;
	
	@Autowired
	public PedidoRepository pedidoRepo;
	
	@Autowired
	public PagamentoRepository pgtRepo;
	
	@Autowired
	public ItemPedidoRepository ipRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Categoria cat1 = new Categoria(null, "Informátca");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepo.saveAll(Arrays.asList(cat1, cat2));
		prodRepo.saveAll(Arrays.asList(prod1, prod2, prod3));

		Estado e1 = new Estado(null, "Goiás");
		Estado e2 = new Estado(null, "Santa Catarina");
		
		Cidade c1 = new Cidade(null, "Anápolis", e1);
		Cidade c2 = new Cidade(null, "Florianópolis", e2);
		
		e1.getCidade().addAll(Arrays.asList(c1));
		e2.getCidade().addAll(Arrays.asList(c2));
		
		estadoRepo.saveAll(Arrays.asList(e1, e2));
		cidadeRepo.saveAll(Arrays.asList(c1, c2));
		
		Cliente cli1 = new Cliente(null, "jean@gmail.com", " 4569871232", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("33332222", "22223333"));
		
		Endereco end1 = new Endereco(null, "Rua das areais", "22", "Praçã das areias", "Campeche", "75783320", cli1, c2);
		Endereco end2 = new Endereco(null, "Ruas das Flores", "33", "Apt 203", "Jardim", "45623218", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		cliRepo.saveAll(Arrays.asList(cli1));
		endRepo.saveAll(Arrays.asList(end1, end2));
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 00:00"), cli1, end2);
		
		Pagamento pagt1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagt1);
		
		Pagamento pagt2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE,ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
		
		pgtRepo.saveAll(Arrays.asList(pagt1, pagt2));
		
		ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));
		
		ipRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
