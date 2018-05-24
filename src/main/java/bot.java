import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.concurrent.ThreadLocalRandom;

public class bot extends TelegramLongPollingBot {

    private long actual_chatId;

    private int order7c1;
    //-------GOD-------------
    private int ordergod;
    private Integer godID;
    private Long godChatID;
    //-----------------------

    private int MSGorPHT; //1 - MSG | 2 - PHOTO | 3 - VIDEO

    private String _photo;
    private Long _chatid;
    private String _text;
    private Integer _reply;

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

    public void onUpdateReceived(Update update) {

        log log = new log();

        if (actual_chatId == update.getMessage().getChatId()) {
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(),update.getMessage().getChat().getTitle(), update.getMessage().getText(), 4);
        } else {
            if (update.getMessage().getChat().isUserChat()) {
                System.out.println("#User: " + update.getMessage().getFrom().getUserName());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(),update.getMessage().getChat().getTitle(), update.getMessage().getText(), 0);
            } else if (update.getMessage().getChat().isGroupChat()) {
                System.out.println("#Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(),update.getMessage().getChat().getTitle(), update.getMessage().getText(), 1);
            } else if (update.getMessage().getChat().isChannelChat()) {
                System.out.println("#Channel: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(),update.getMessage().getChat().getTitle(), update.getMessage().getText(), 2);
            } else if (update.getMessage().getChat().isSuperGroupChat()) {
                System.out.println("#S-Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(),update.getMessage().getChat().getTitle(), update.getMessage().getText(), 3);
            } else {
                System.out.println("#WTF: " + update.getMessage().getChat().getTitle());
            }
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            actual_chatId = update.getMessage().getChatId();
        }

        //----------------------------------------------------

        //....................................................
        //Clear message
        order7c1 = 0;
        MSGorPHT = 0;
        _text = null;
        _photo = null;
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
            _photo = "https://thumbs.gfycat.com/BlandOrnateGreatwhiteshark-max-1mb.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("what".equals(update.getMessage().getText()) || "WHAT".equals(update.getMessage().getText()) || "What".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://media.giphy.com/media/5QTLUC40nxc1lobJGp/giphy.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if (update.getMessage().getText().contains("bot")) {

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

        } else if (update.getMessage().getText().contains("intentar ")) {
            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = null;

            String message = update.getMessage().getText();
            String[] parts = message.split("intentar ");
            String part1 = parts[0]; // intentar
            String part2 = parts[1]; // intento

            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);

            if(randomNum == 2) {
                _text = update.getMessage().getFrom().getFirstName() + " intento " + part2 + " y lo consigue";
            } else {
                _text = update.getMessage().getFrom().getFirstName() + " intento " + part2 + " pero falló";
            }

        } else if (update.getMessage().getText().contains("7c1")) {
            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
            _text = "Ejecutando Orden 7c1";
            order7c1 = 1;
        }

        //----- GOD ORDERS ---------
        if (ordergod == 0) {
            if (update.getMessage().getText().contains("setgod")) {
                MSGorPHT = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "Establecido como Dios";
                ordergod = 1;
                godID = update.getMessage().getFrom().getId();
                godChatID = update.getMessage().getChatId();
            }
        }

        if(update.getMessage().getFrom().getId().equals(godID)) {
            if (update.getMessage().getText().contains("info-dios")) {
                MSGorPHT = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "Dios establecido: @" + update.getMessage().getFrom().getUserName() + "\nDios ID: " + godID;
            } else if (update.getMessage().getText().contains("info-chat")) {
                MSGorPHT = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "Chat actual: " + update.getMessage().getChat().getTitle() + "\nChat ID: "+ update.getMessage().getChat().getId();
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
        }

        if (order7c1 == 1) {
            String grpName = update.getMessage().getChat().getTitle();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _text = "Llamando a mi creador...";
            _reply = null;
            _chatid = update.getMessage().getChatId();

            //sending order 7c1
            try {
                execute(s_msg(_text,_chatid,_reply));
                System.out.println("Orden 7c1 ejecutada");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //-----------------------------------------------------------------------------
            _text = "Te llaman del grupo: " + grpName + "\nChat ID: " + _chatid;
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
