package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessosController {

	public ProcessosController() {
		super();
	}

	// Retorna o S.O que está em execução na máquina.
	public String os() {
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		return os + " - Versão: " + version + " - Arquitetura: " + arch;
	}

	public void callProcess(String process) {
		try {
			Runtime.getRuntime().exec(process);
		} catch (IOException e) {
			String msgErro = e.getMessage();
			System.err.println(msgErro);

			// Somente Windows

			if (os().contains("Windows")) {
				// CTRL + SHIFT + I --> Teste Lógico
				if (msgErro.contains("740")) {
					// Garantir privilégios de administrador
					// cmd /c caminho_do_processo
					StringBuffer buffer = new StringBuffer();
					buffer.append("cmd /c");
					buffer.append(" ");
					buffer.append(process);
					try {
						Runtime.getRuntime().exec(buffer.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	public void readProcess(String process) {
		try {
			Process p = Runtime.getRuntime().exec(process);
			// Fluxo de bits que vem do Process p
			InputStream fluxo = p.getInputStream();
			// Transforma o fluxo em String
			InputStreamReader leitor = new InputStreamReader(fluxo);
			// Armazena a informação do leitor em um buffer
			BufferedReader buffer = new BufferedReader(leitor);
			// Leitura do buffer
			String linha = buffer.readLine();
			while (linha != null) {
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void killProcess(String param) {
		if (os().contains("Windows")) {
			String cmdPid = "TASKKILL /PID";
			String cmdNome = "TASKKILL /IM";
			int pid = 0;
			StringBuffer buffer = new StringBuffer();

			try {
				// TASKKILL /PID XXXX
				pid = Integer.parseInt(param);
				buffer.append(cmdPid);
				buffer.append(" ");
				buffer.append(pid);
			} catch (NumberFormatException e) {
				// TASKKILL /IM nomeProcess.exe
				buffer.append(cmdNome);
				buffer.append(" ");
				buffer.append(param);
			}
			callProcess(buffer.toString());
		} else {
			String cmdPid = "kill -9";
			String cmdNome = "pkill -f";
			int pid = 0;
			StringBuffer buffer = new StringBuffer();
			
			try {
				pid = Integer.parseInt(param);
				buffer.append(cmdPid);
				buffer.append(" ");
				buffer.append(param);
			} catch (NumberFormatException e) {
				buffer.append(cmdNome);
				buffer.append(" ");
				buffer.append(param);
			}
			callProcess(buffer.toString());
		}
	}

}
