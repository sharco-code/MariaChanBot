import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class bot extends TelegramLongPollingBot {

    private long actual_chatId;

    private int ordercda;
    //-------GOD-------------
    private int ordergod;
    private int ordergod2;
    private String godName;
    private String godName2;
    private Integer godID2;
    private Integer godID;
    private Long godChatID2;
    private Long godChatID;
    //-----------------------

    private int MSGorPHT; //1 - MSG | 2 - PHOTO | 3 - VIDEO | 4 - FILE

     private String _photo;
    private Long _chatid;
    private String _text;
    private Integer _reply;
    private File _document;

    public SendMessage s_msg(String msgText, Long msgChatid, Integer msgReply) {
        SendMessage s_msg = new SendMessage();
        s_msg.setText(msgText);
        s_msg.setChatId(msgChatid);
        s_msg.setReplyToMessageId(msgReply);
        return s_msg;
    }

    public SendPhoto s_pht(String phtCapton, String phtPhoto, Long phtChatid, Integer phtReply) {
        SendPhoto s_pht = new SendPhoto();
        s_pht.setCaption(phtCapton);
        s_pht.setPhoto(phtPhoto);
        s_pht.setChatId(phtChatid);
        s_pht.setReplyToMessageId(phtReply);
        return s_pht;
    }

    public SendVideo s_vid(String vidCapton, String vidPhoto, Long vidChatid, Integer vidReply) {
        SendVideo s_vid = new SendVideo();
        s_vid.setCaption(vidCapton);
        s_vid.setVideo(vidPhoto);
        s_vid.setChatId(vidChatid);
        s_vid.setReplyToMessageId(vidReply);
        return s_vid;
    }

    public SendDocument s_doc(String docCaption, File fileDocumment, Long docChatid, Integer docReply) {
        SendDocument s_doc = new SendDocument();
        s_doc.setCaption(docCaption);
        s_doc.setChatId(docChatid);
        s_doc.setReplyToMessageId(docReply);
        s_doc.setNewDocument(fileDocumment);
        return s_doc;
    }

    private String Command(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    private ArrayList<String> userNames = new ArrayList<String>();

    public String logNames() {
        String output = "";

        for (String x : userNames) {
            System.out.println(x);
            output = output + "\n" + x;
        }

        return output;
    }


    public void onUpdateReceived(Update update) {

        log log = new log();

        if (actual_chatId == update.getMessage().getChatId()) {
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 0);
        } else {
                    if (update.getMessage().getChat().isUserChat()) {
                System.out.println("#User: " + update.getMessage().getFrom().getUserName());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 0);
                if(!userNames.contains("@" + update.getMessage().getFrom().getUserName())) {
                    userNames.add("@" + update.getMessage().getFrom().getUserName());
                }
            } else if (update.getMessage().getChat().isGroupChat()) {
                System.out.println("#Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 1);

                if(!userNames.contains(update.getMessage().getChat().getTitle())) {
                    userNames.add(update.getMessage().getChat().getTitle());
                }
            } else if (update.getMessage().getChat().isChannelChat()) {
                System.out.println("#Channel: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 2);
                        if(!userNames.contains(update.getMessage().getChat().getTitle())) {
                            userNames.add(update.getMessage().getChat().getTitle());
                        }
            } else if (update.getMessage().getChat().isSuperGroupChat()) {
                System.out.println("#S-Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 3);
                        if(!userNames.contains(update.getMessage().getChat().getTitle())) {
                            userNames.add(update.getMessage().getChat().getTitle());
                        }
            } else {
                System.out.println("#WTF: " + update.getMessage().getChat().getTitle());
            }
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            actual_chatId = update.getMessage().getChatId();
        }

        //----------------------------------------------------

        //....................................................
        //Clear message
        ordercda = 0;
        MSGorPHT = 0;
        _text = null;
        _photo = null;
        _document = null;
        //....................................................
        if ("omae wa mou shindeiru".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://i.makeagif.com/media/2-21-2015/RDVwim.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("nani".equals(update.getMessage().getText()) || "NANI".equals(update.getMessage().getText()) || "Nani".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://pa1.narvii.com/6715/4c6e81d7a5f2f5f642f40cf23a3cfe19881cb76e_hq.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("aaa perro traes el omnitrix".equals(update.getMessage().getText()) || "Aaa perro traes el omnitrix".equals(update.getMessage().getText())) {
            MSGorPHT = 2;
            _text = null;
            _photo = "https://s3.amazonaws.com/glr-fileserver/Larepublica/2018/03/04/facebook-omnitrix7-1520172526.jpg";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("what".equals(update.getMessage().getText()) || "WHAT".equals(update.getMessage().getText()) || "What".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://media.giphy.com/media/5QTLUC40nxc1lobJGp/giphy.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("/start".equals(update.getMessage().getText())) {
            MSGorPHT = 1;
            _text = EmojiParser.parseToUnicode("Hola " + update.getMessage().getFrom().getFirstName() + ", puedes contarme tus secretos :wink:");
            _photo = null;
            _chatid = update.getMessage().getChatId();
            _reply = null;
        } else if ("maria bot que opinas?".equals(update.getMessage().getText())) {
            MSGorPHT = 1;
            _text = EmojiParser.parseToUnicode("que es una kk :no_mouth:");
            _photo = null;
            _chatid = update.getMessage().getChatId();
            _reply = null;
        } else if (update.getMessage().getText().contains("mariabot") || update.getMessage().getText().contains("Mariabot") || update.getMessage().getText().contains("MariaBot")) {

            //generate random number
            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);

            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = null;

            //all phrases
            if (update.getMessage().getText().contains("puto") || update.getMessage().getText().contains("tonto") || update.getMessage().getText().contains("inutil") || update.getMessage().getText().contains("subnormal") || update.getMessage().getText().contains("hijoputa") || update.getMessage().getText().contains("maricon") || update.getMessage().getText().contains("cabron") || update.getMessage().getText().contains("idiota") || update.getMessage().getText().contains("baka")) {
                if (randomNum == 1) {
                    _text = "eh, sin insultar xd";
                } else if (randomNum == 2) {
                    _text = "no digas palabrotas";
                } else if (randomNum == 3) {
                    _text = ":c";
                } else {
                    _text = "vaya tela...";
                }
            } else {
                if (randomNum == 1) {
                    _text = "claro, es por que soy un bot verdad?";
                } else if (randomNum == 2) {
                    _text = "que pasa aqui?";
                } else if (randomNum == 3) {
                    _text = "hablando de bots... xD";
                } else {
                    _text = "uwu";
                }
            }
        } else if (update.getMessage().getText().contains(":(")) {
            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();

            int randomNum = ThreadLocalRandom.current().nextInt(0, 1);

            if(randomNum == 0) {
                _text = "Sonrie princesa";
            } else if (randomNum == 1) {
                _text = "no estes triste";
            }

        } else if (update.getMessage().getText().contains("ntentar ")) {
            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = null;

            String message = update.getMessage().getText();
            String[] parts = message.split("ntentar ");
            String part1 = parts[0]; // intentar
            String part2 = parts[1]; // intento

            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);

            if(randomNum == 2) {
                _text = EmojiParser.parseToUnicode(":game_die: " + update.getMessage().getFrom().getFirstName() + " intento " + part2 + " y lo consigue");
            } else {
                _text = EmojiParser.parseToUnicode(":game_die: " + update.getMessage().getFrom().getFirstName() + " intento " + part2 + " pero falló");
            }

        } else if (update.getMessage().getText().contains("charcodeagua")) {
            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
            _text = "Ejecutando Orden <charco de agua>";
            ordercda = 1;
        } else if (update.getMessage().getText().contains("ugerencia: ")) {
            MSGorPHT = 1;
            String message = update.getMessage().getText();
            String[] parts = message.split("ugerencia: ");
            String part1 = parts[0];
            String part2 = parts[1];

            BufferedWriter bw = null;
            FileWriter fw = null;

            try {
                    fw = new FileWriter(".sugerencias.txt", true);
                String text = "@" + update.getMessage().getFrom().getUserName() + " - " + update.getMessage().getFrom().getFirstName() + ": " + part2 + "\n";
                bw = new BufferedWriter(fw);
                bw.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {

                try {

                    if (bw != null)
                        bw.close();

                    if (fw != null)
                        fw.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }

            }

            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId() ;
            _text = EmojiParser.parseToUnicode("Sugerencia guardada, gracias " + update.getMessage().getFrom().getFirstName() + "! :smile:");
        }

        //----- GOD ORDERS ---------
        if ("/setgod".equals(update.getMessage().getText())) {
                if (ordergod == 0) {
                    MSGorPHT = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    ordergod = 1;
                    godID = update.getMessage().getFrom().getId();
                    godChatID = update.getMessage().getChatId();
                    godName = update.getMessage().getFrom().getUserName();
                    _text = "Estableciendo nuevo dios 1: @" + update.getMessage().getFrom().getUserName() + "\nDios ID: " + godID;
                } else if (ordergod2 == 0) {
                    MSGorPHT = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    ordergod2 = 1;
                    godID2 = update.getMessage().getFrom().getId();
                    godChatID2 = update.getMessage().getChatId();
                    godName2 = update.getMessage().getFrom().getUserName();
                    _text = "Estableciendo nuevo dios 2: @" + update.getMessage().getFrom().getUserName() + "\nDios ID: " + godID2;
            }
        }

        if(update.getMessage().getFrom().getId().equals(godID) || update.getMessage().getFrom().getId().equals(godID2)) {
            if (update.getMessage().getText().contains("/run ")) {
                String message = update.getMessage().getText();
                String[] parts = message.split("/run ");
                String part1 = parts[0];
                String part2 = parts[1];
                MSGorPHT = 1;
                _text = Command(part2);
                _reply = update.getMessage().getMessageId();
                _chatid = update.getMessage().getChatId();
            }  else if ("/reg".equals(update.getMessage().getText())) {
                MSGorPHT = 1;
                //_text = Command("ls");
                _text = logNames();
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
            } else if ("/unsetgod".equals(update.getMessage().getText())){
                if(ordergod == 1 || ordergod2 == 1){
                    if(ordergod == 1) {
                        MSGorPHT = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "@" + update.getMessage().getFrom().getUserName() + " ya no es el dios 1";
                        ordergod = 0;
                        godID = null;
                        godName = null;
                        godChatID = null;
                    } else if (ordergod2 == 1) {
                        MSGorPHT = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "@" + update.getMessage().getFrom().getUserName() + " ya no es el dios 2";
                        ordergod2 = 0;
                        godID2 = null;
                        godName2 = null;
                        godChatID = null;
                    }
                }
            } else if (update.getMessage().getText().contains("/info")) {
                String message = update.getMessage().getText();
                String[] parts = message.split("/info ");
                String part1 = parts[0];
                String part2 = parts[1];
                switch (part2) {
                    case "god 1":
                        if(godName != null) {
                            MSGorPHT = 1;
                            _chatid = update.getMessage().getChatId();
                            _reply = update.getMessage().getMessageId();
                            _text = "Dios 1 establecido: @" + godName + "\nDios ID: " + godID;
                        } else {
                            MSGorPHT = 1;
                            _chatid = update.getMessage().getChatId();
                            _reply = update.getMessage().getMessageId();
                            _text = "No hay dios 1 establecido";
                        }
                        break;
                    case "god 2":
                        if(godName2 != null) {
                            MSGorPHT = 1;
                            _chatid = update.getMessage().getChatId();
                            _reply = update.getMessage().getMessageId();
                            _text = "Dios 2 establecido: @" + godName2 + "\nDios ID: " + godID2;
                        } else {
                            MSGorPHT = 1;
                            _chatid = update.getMessage().getChatId();
                            _reply = update.getMessage().getMessageId();
                            _text = "No hay dios 2 establecido";
                        }
                        break;
                    case "chat":
                        MSGorPHT = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        if (update.getMessage().getChat().isUserChat()) {
                            _text = "Chat actual: @" + update.getMessage().getChat().getUserName() + "\nChat ID: "+ update.getMessage().getChat().getId();
                        } else if (update.getMessage().getChat().isGroupChat()) {
                            _text = "Chat actual: " + update.getMessage().getChat().getTitle() + "\nChat ID: "+ update.getMessage().getChat().getId();
                        } else {
                            _text = "*Chat actual: " + update.getMessage().getChat().getTitle() + "\nChat ID: "+ update.getMessage().getChat().getId();
                        }
                        break;
                    default:
                        MSGorPHT = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "No puedo mostrar informacion sobre <" + part2 + ">";
                        System.out.println("Error en /info switch");
                        break;
                }

            } else if (update.getMessage().getText().contains("/get")) {
                MSGorPHT = 4;
                _chatid = update.getMessage().getChatId();
                String message = update.getMessage().getText();
                String[] parts = message.split("\\s+");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];

                switch (part2) {
                    case "chat":
                        File file = new File(part3 + ".txt");
                        _document = file;
                        _reply = update.getMessage().getMessageId();
                        _text = null;
                        break;
                    case "sugerencias":
                        File sugerencias = new File(".sugerencias.txt");
                        _document = sugerencias;
                        _reply = update.getMessage().getMessageId();
                        _text = null;
                        break;
                    default:
                        MSGorPHT = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "No puedo enviarte <" + part2 + ">";
                        System.out.println("Error en /get switch");
                        break;
                }

            }  else if (update.getMessage().getText().contains("/help")) {
                MSGorPHT = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "COMANDOS:\n/run\n/reg\n/help\n/info <algo>\n/get <algo> <algo de algo>\nZONA PELIGROSA\n/unsetgod";
            }
        }
        //----- ----------- ---------
        if (MSGorPHT == 1) {
            try {
                execute(s_msg(_text, _chatid, _reply));
                System.out.println("Mensaje envido...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (MSGorPHT == 2) {
            try {
                sendPhoto(s_pht(_text, _photo, _chatid, _reply));
                System.out.println("Foto enviada...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (MSGorPHT == 3) {
            try {
                sendVideo(s_vid(_text, _photo, _chatid, _reply));
                System.out.println("Video/Gif enviado...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (MSGorPHT == 4) {
            try {
                sendDocument(s_doc(_text, _document, _chatid, _reply));
                System.out.println("Documento enviado...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (ordercda == 1) {
            String grpName = update.getMessage().getChat().getTitle();
            String usName = update.getMessage().getFrom().getUserName();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _text = "Llamando a mi primer dios ...";
            _reply = null;
            _chatid = update.getMessage().getChatId();

            //sending order 7c1
            try {
                execute(s_msg(_text,_chatid,_reply));
                System.out.println("Orden charco de agua ejecutada");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //-----------------------------------------------------------------------------
            if (update.getMessage().getChat().isUserChat()) {
                _text = "Te llama @" + usName + "\nChat ID: " + _chatid;
            } else if (update.getMessage().getChat().isGroupChat()) {
                _text = "Te llaman del grupo: " + grpName + "\nChat ID: " + _chatid;
            } else {
                _text = "*Te llaman del grupo: " + grpName + "\nChat ID: " + _chatid;
            }

            _chatid = godChatID;
            _reply = null;
            try {
                execute(s_msg(_text, _chatid, _reply));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
    public String getBotUsername() { return "BOT_USERNAME"; }

    public String getBotToken() { return "BOT_TOKEN"; }
    }
