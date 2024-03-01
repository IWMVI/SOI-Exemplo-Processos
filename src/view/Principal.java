package view;

import controller.ProcessosController;

public class Principal {
	public static void main(String[] args) {
		ProcessosController procC = new ProcessosController();

		String os = procC.os();
		System.out.println(os);

		String process;

		process = "gnome-terminal";
		procC.callProcess(process);
		process = "gedi";
		procC.callProcess(process);

		if (procC.os().contains("Linux")) {
			process = "ps -ef";
			procC.readProcess(process);
			process = "ping -4 -c 2 www.google.com.br";
			procC.readProcess(process);
			System.out.println("Rota");
			process = "traceroute www.google.com";
			procC.readProcess(process);
			process = "ip addr";
			procC.readProcess(process);
			process = "gnome-terminal";
			procC.killProcess(process);
//			process = "16802";
//			procC.killProcess(process);
		} else if (procC.os().contains("windows")) {
			process = "IPCONFIG";
			procC.readProcess(process);
			process = "ping -4 -t www.google.com";
			procC.readProcess(process);
			process = "TRACERT www.google.com";
			procC.callProcess(process);
			process = "IPCONFIG";
			procC.callProcess(process);
		}
	}
}
