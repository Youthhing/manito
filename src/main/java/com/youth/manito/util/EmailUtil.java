package com.youth.manito.util;

import com.youth.manito.domain.entity.Manito;

public class EmailUtil {

    public static final String MATCH_MANITO_SUBJECT = "[신승유기] 마니또 매칭 결과 안내드립니다.";

    private static final String BASE_HEADER = "<html>\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>신승유기 마니또 매칭 결과 안내</title>\n"
            + "    <style>\n"
            + "        body { font-family: Arial, sans-serif; color: #333; background-color: #f8f9fa; margin: 0; padding: 0; }\n"
            + "        .container { width: 100%; max-width: 600px; margin: 20px auto; background: #fff; border: 1px solid #ddd; padding: 20px; }\n"
            + "        h1 { font-size: 22px; color: #007bff; margin-bottom: 10px; }\n"
            + "        .section { margin-bottom: 20px; border-bottom: 1px solid #ddd; padding-bottom: 10px; }\n"
            + "        .section:last-child { border-bottom: none; }\n"
            + "        .button-container { text-align: center; margin-top: 20px; }\n"
            + "        .button { display: inline-block; background-color: #28a745; color: #fff !important; text-decoration: none; padding: 10px 20px; border-radius: 4px; font-weight: bold; }\n"
            + "        .footer { text-align: center; font-size: 12px; color: #777; margin-top: 20px; }\n"
            + "    </style>\n"
            + "</head>\n";

    public static String getMatchManitoMessageBody(final Manito manito, final String voteLink) {
        return "<!DOCTYPE html>\n"
                + BASE_HEADER
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <h1>신승유기 마니또 매칭 결과 안내</h1>\n"
                + "        <div class=\"section\">\n"
                + "            <strong>마니또란?</strong><br>\n"
                + "            - 특정 인원에게 잘해줘야하는 사람<br><br>\n"
                + "            ex) 신유승의 마니또 = 신유승에게 잘해줘야하는 사람\n"
                + "        </div>\n"
                + "        <div class=\"section\">\n"
                + "            <p><strong>" + manito.getGiver().getName() + "</strong>님은 <strong>"
                + manito.getReceiver().getName() + "</strong>의 마니또 입니다.</p>\n"
                + "        </div>\n"
                + "        <div class=\"section\">\n"
                + "            <strong>미션</strong><br>\n"
                + "            - 게임 종료까지 <strong>" + manito.getReceiver().getName() + "</strong>님께 아래 질문을 티나지 않게 해주세요!\n"
                + "            <p> 질문: " + manito.getMission().getContents() + " </p>\n"
                + "        </div>\n"
                + "        <div class=\"button-container\">\n"
                + "            <a href=\""+ voteLink + "?giverEmail=" + manito.getGiver().getEmail() +"\" class=\"button\">투표하러가기</a>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"footer\">© 신승유기 - All rights reserved.</div>\n"
                + "</body>\n"
                + "</html>";

    }
}
