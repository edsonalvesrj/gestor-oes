package br.com.base.controller;

import br.com.base.controlles.ControllView;
import br.com.base.controlles.SplashView;
import br.com.base.util.HibernateUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    private  static  ControllView controle; 
    @Override
    public void start(Stage stage) throws Exception {
            //evento ja incializado para fechar todas as abas ou janelas que estiverem abertas 
            //  ControllView controle = new ControllView();
            controle = ControllView.PegaInstancia();           
            stage.setOnCloseRequest(event -> {
            controle.CloseStage();
            System.exit(0);

        });

        // iniciando barra de progresso 
        SplashView splash = new SplashView();

        //inicicando stage com tela inicial login
        Task<Void> threadPanes = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { 
                        controle.loadView("MAPLOGIN", "/fxml/LoginView.fxml");
                        controle.setView("MAPLOGIN");
                    }

                });

                return null;
            }
        };

        // inicializando base de dados hibernate
        Task<Void> taskhbnt = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HibernateUtil.getSessionFactory();

                    }
                });

                return null;
            }
        };

        //  executando threads  de inicializaçao 
        Thread t1 = new Thread(splash);
        Thread t3 = new Thread(threadPanes);
        Thread t2 = new Thread(taskhbnt);

        // inserindo prioridade  de inicialização de  base de dados hibernate 
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

    }

    public static void main(String[] args) {
        launch(args);

    }
  

}
