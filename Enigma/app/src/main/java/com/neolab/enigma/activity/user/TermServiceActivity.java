package com.neolab.enigma.activity.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;

/**
 * Display term and condition using service
 *
 * @author Pika
 */
public class TermServiceActivity extends BaseActivity implements View.OnClickListener{

    private CheckBox mAgreeCheckBox;
    private TextView mTitleTextView;
    private View mAgreeTermLayout;
    private Button mAgreeButton;
    private View mBackLayout;
    private WebView mTermAndConditionWebView;

    private String mNameOfUser;
    private final String htmlText = " <!DOCTYPE html>\n" +
            "            <html>\n" +
            "              <head>\n" +
            "                <meta charset=\"utf-8\" />\n" +
            "                <meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\" />\n" +
            "                <style>\n" +
            "                    .content {\n" +
            "                        font-size: 10px\n" +
            "                    }\n" +
            "                    .align-center {\n" +
            "                        text-align: center;\n" +
            "                    }\n" +
            "                    .align-right {\n" +
            "                        text-align: right;\n" +
            "                    }\n" +
            "                </style>\n" +
            "              </head>\n" +
            "            <body>\n" +
            "                <div class=\"content\">\n" +
            "                    <p class=\"align-center\">前払給与立替払いサービス利用規約</p>\n" +
            "                    <p class=\"align-right\">平成29年2月15日制定</p>\n" +
            "                    <p>第１条（本利用規約の趣旨及び適用範囲）<br />　１　この規約（以下「本規約」といいます。）は、株式会社ｅｎｉｇｍａ（以下「当社」といいます。）が提供する前払給与の立替払サービス「ｅｎｉｇｍａ」（以下「本サービス」といいます。）を導入している企業（以下「導入企業」といいます。）の従業員（以下「利用者」といいます。）が本サービスを利用する際の、当社と利用者との間の権利義務関係を定めたものです（以下、本規約を内容とする当社と利用者との契約を「本契約」といいます。）。</p>\n" +
            "                    <p>　２　本サービスに関し、当社が運営するウェブサイトをはじめ、当社が必要に応じて別途定める諸規定（当社による利用者への通知、ガイドライン、ヘルプ、注意書き等を含みますが、これらに限られません。）も本規約の一部を構成します。なお、これらが本規約との間に矛盾抵触がある場合には、本規約が優先して適用されるものとします。</p>\n" +
            "                    <p><br />第２条（総則）<br />　１　利用環境<br />　　　本サービスは、インターネットに接続されている等当社所定のシステム環境を備えた端末を適法に使用する権限を有する利用者に限り利用ができます。ただし、当社所定のシステム環境を備えた端末においても、利用者による個別設定の状況により、利用ができない場合があります。</p>\n" +
            "                    <p>　２　利用時間<br />　　　本サービスは、原則として、導入企業所定の時間内に限り利用することができます。</p>\n" +
            "                    <p>　３　サービス内容の変更又は廃止<br />　　　当社は、監督官庁の指導、法令の改正、新たな立法又は監督官庁による法解釈の変更等があった場合、その他必要がある場合には、任意の判断で、利用者に事前に通知することなく、本サービスの全部又は一部を変更又は廃止することができるものとします。ただし、当社が本サービスの全部を廃止する場合には、利用者に事前に通知するものとします。</p>\n" +
            "                    <p><br />第３条（本サービスの申込及び利用開始）<br />　１　利用者は、当社所定の手続に従い本サービスの利用を申し込むものとし、導入企業及び当社の承諾並びに所定の手続を完了したときから本サービスを利用できるものとします。</p>\n" +
            "                    <p>　２　本サービスの申込資格者は、導入企業に雇用されている従業員に限ります。</p>\n" +
            "                    <p>　３　利用者による本サービスの利用開始までの手続は、当社が定める手続に依拠するものとします。</p>\n" +
            "                    <p><br />第４条（パスワード認証）<br />　１　利用者は、当社所定の申込手続を完了したときは、当社又は導入企業から、本サービス利用時に必要となる企業ＩＤ、利用者番号及び初期パスワードを付与されます。</p>\n" +
            "                    <p>　２　初期パスワードの変更<br />　　　利用者は、初めて本サービスを利用する際に、端末の操作により当社所定の方法で、初期パスワードの変更を行うこととします。この変更手続により届けられたパスワードを、本サービスを利用する際の正規パスワード（以下「正規パスワード」といいます。）とし、正規パスワードを取得する前は、本サービスの利用はできません。</p>\n" +
            "                    <p>　３　正規パスワードの変更<br />　　　利用者は、当社所定の手続に従い、随時、正規パスワードを変更することができます。正規パスワードを失念した場合、当社所定の手続に従い、パスワードの初期化を行い、正規パスワードを再取得するものとします。</p>\n" +
            "                    <p>　４　パスワード等の管理<br />　　　企業ＩＤ、利用者番号、パスワード（正規及び初期パスワードの双方。以下同じ。）は、利用者の責任において厳重に管理するものとし、第三者に利用させ、又は貸与、譲渡、名義変更、売買、開示等をしないものとします。なお、利用者の企業ＩＤ、利用者番号及びパスワードを利用してなされた行為については、当該利用者の行為とみなされ、利用者は係る行為及びその結果について一切の責任を負うものとします。</p>\n" +
            "                    <p>　５　パスワードの盗難又は不正利用<br />　　　利用者は、パスワードについて盗用又は不正使用等のおそれがある場合、当社所定の手続により、直ちにパスワードを変更するものとします。</p>\n" +
            "                    <p>　６　利用停止<br />　　　本サービス利用に際し、当社に登録されたパスワードと異なるパスワードが、当社所定の回数以上連続して入力されたときは、当該利用者番号による本サービスの利用を停止します。当該利用停止により利用者に損害が生じたとしても、当社は責任を負いません。本サービスの利用が停止された場合、利用者は、当社所定の手続でパスワードの初期化及び再取得を行うことにより、本サービスの利用停止を解除することができます。</p>\n" +
            "                    <p><br />第５条（本サービスの利用）<br />　１　当社は、利用者による認証手続において、企業ＩＤ、利用者番号及びパスワードが、当社に登録されているものと一致することが確認されたときは、正当な利用者による利用であると認め、本サービスの提供を行うものとします。</p>\n" +
            "                    <p>　２　利用者は、本サービスに基づく給与前払を希望する場合には、前項の認証を受けた後、当社所定の方法で、前払を希望する額（以下「申請額」といいます。）を当社に申請するものとします。この申請は、当社が次項の振込手続に着手した場合には、撤回又は変更できません。</p>\n" +
            "                    <p>　３　当社は、前項の申請を受けた後、申請額が本サービス所定の上限を超えない場合に限り、導入企業に代わり、利用者に対し申請額を振り込むものとします。なお、振込手数料は利用者の負担とします。</p>\n" +
            "                    <p>　４　当社は、前項の振込時に、振込手数料及び次条所定の利用手数料を差し引くものとし、利用者はこれを承諾します。</p>\n" +
            "                    <p><br />第６条（利用手数料）<br />　　本サービスの利用手数料は、利用者への給与前払１回あたり、申請額の６％（税込）とします。</p>\n" +
            "                    <p><br />第７条（届出事項の変更等）<br />　１　利用者は、本サービスの利用にあたり当社又は導入企業に届け出た事項に変更がある場合、直ちに、当社所定の方法で届出を行うものとします。</p>\n" +
            "                    <p>　２　利用者が前項の届出を怠ったことにより生じた損害について、当社は責任を負わないこととします。</p>\n" +
            "                    <p><br />第８条（表明及び保証）<br />　　利用者は、当社に対して提供した一切の情報（導入企業を介して提供した情報を含みます。）が、全て真実かつ正確であることを表明し保証するものとします。</p>\n" +
            "                    <p><br />第９条（利用停止、解除及び解約）<br />　１　本サービスの利用停止<br />　　　各号に該当する事由がひとつでも生じたときは、当社は、利用者に通知することなく、本サービスの全部又は一部の利用を直ちに停止することができるものとします。<br />　　(1)　導入企業からの依頼に基づき、本サービスの利用を停止する場合<br />　　(2)　利用者が本規約に違反した場合<br />　　(3)　その他、当社が本サービスの利用停止の必要を認める相当の事由が存在する場合</p>\n" +
            "                    <p>　２　本契約の解除<br />　　　利用者に次の各号の一に該当する事由があるときは、当社は、本契約を直ちに解約することができるものとします。<br />　　(1)　本規約に違反したとき<br />　　(2)　支払の停止、破産・民事再生その他の法的整理手続開始の申立てがあったとき<br />　　(3)　公租公課の滞納処分があったとき<br />　　(4)　仮差押、仮処分、強制競売手続開始、滞納処分があったとき<br />　　(5)　その他、信用状態に重大な変化が生じたと当社において判断したとき<br />　　(6)　本契約に関し、申込み又は届出事項に虚偽の事項があったとき<br />　　(7)　その他、本契約を継続し難い相当の事由が生じたとき</p>\n" +
            "                    <p>　３　本契約の解約<br />　　　利用者は、当社所定の手続により、本契約を解約することができるものとします。</p>\n" +
            "                    <p><br />第１０条（免責事項）<br />　１　当社は、次の各号に定める場合における責任を負いません。<br />　　(1)　不正な認証、本人確認手段の不正使用等<br />　　　　企業ＩＤ、利用者番号、パスワードが発行された後、これらの偽造、変造、盗用、その他の不正行為により損害が生じた場合<br />　　(2)　通信手段等の障害等<br />　　　　通信機器、専用電話回線、公衆電話回線、インターネット及びコンピューター等の障害等により本サービスの利用ができない場合<br />　　(3)　通信経路における取引情報の漏洩等<br />　　　　通信機器、専用電話回線、公衆電話回線、インターネット等の通信経路において、盗聴、不正アクセス等、企業ＩＤ、利用者番号、パスワード、秘密情報等が流出したことにより損害が生じた場合</p>\n" +
            "                    <p>　２　利用者は、自らの責任において本サービスを利用するものとし、本サービスの利用により、又は本サービスを利用できなかったことにより、何らかの損害が発生した場合でも、名目及び金額の如何を問わず、当社にその賠償を請求できないものとします。</p>\n" +
            "                    <p>　３　当社は、次の各号について何らの保証をしないものとします。<br />　　(1)　導入企業が利用者に提供する給与前払い制度に関する一切の事項<br />　　(2)　所定のブラウザソフトの内容、状態、機能、作用等に関する事項<br />　　(3)　本サービスへのインターネット接続及びその利用、又は障害が発生しないこと</p>\n" +
            "                    <p>　４　当社は、前条に基づく本サービスの全部又は一部の利用停止、解除、解約等により利用者又は第三者に損害が生じた場合であっても、一切の責任を負いません。</p>\n" +
            "                    <p>　５　当社は、戦争、災害、天変地異、法令の制定、裁判所の判断等により、本サービスの提供が困難となったことにより生じた損害について、一切の責任を負いません。</p>\n" +
            "                    <p>　６　当社は、本サービスの利用に関連して、利用者と導入企業その他の第三者との間において生じた連絡、紛争等については、一切の責任を負いません。ただし、本サービスに関する質問、苦情については、当社に連絡するものとします。</p>\n" +
            "                    <p>　７　本規約の他の規定に関わらず、本契約が消費者契約法第２条第３項の消費者契約に該当し、かつ、当社が債務不履行又は不法行為に基づき損害賠償責任を負う場合については、当社に故意又は重大な過失がある場合を除いて、当社は、利用者が直接かつ現実に被った通常損害を上限として損害賠償責任を負うものとし、間接損害、特別損害、将来の損害及び逸失利益については責任を負わないものとします。</p>\n" +
            "                    <p><br />第１１条（再委託）<br />　　当社は、本サービスにより提供する機能の全部又は一部を、第三者に再委託することができるものとし、利用者はこれを予め承諾するものとします。</p>\n" +
            "                    <p><br />第１２条（知的財産権）<br />　　本サービスに関する特許権、実用新案権、意匠権、商標権、著作権、営業秘密、ノウハウその他の権利（これらを受ける権利を含み、以下「知的財産権等」と総称します。）は、全て当社に帰属します。本契約に基づく本サービスの利用は、本サービスに関する知的財産権等の移転又は使用許諾その他如何なる権利の移転又は付与も意味するものではありません。</p>\n" +
            "                    <p><br />第１３条（禁止事項）<br />　　利用者は、次の各号の行為を自ら行わず、又は第三者をして行わせないものとします。<br />　(1)　本サービスの利用を第三者に許諾する行為<br />　(2)　当社による本サービスの提供を妨害し、又は妨害するおそれのある行為<br />　(3)　企業ＩＤ、利用車番号、パスワードを偽造、変造、盗用、不正使用する行為<br />　(4)　本サービスの利用にあたり、虚偽の情報を登録又は提出する行為<br />　(5)　コンピューターウィルス等、有害なプログラムを本サービスに関連して使用し、もしくは提供する行為<br />　(6)　本サービスを構成するシステムのリバースエンジニアリング、逆コンパイル、逆アセンブルその他これらに類する行為<br />　(7)　本サービスの複製、翻案、改変等、当社の知的財産権等を侵害し、もしくは侵害するおそれのある行為<br />　(8)　本規約上の権利義務の全部又は一部を他人に譲渡し、承継し又は質入その他の処分をする行為<br />　(9)　本サービスと同様もしくは類似し、又は競合するサービスを構築・提供する行為<br />　(10)　法令に触れる行為<br />　(11)　犯罪にかかわる行為<br />　(12)　公序良俗に反する行為<br />　(13)　その他、当社が不適切と判断する行為</p>\n" +
            "                    <p><br />第１４条（秘密保持）<br />　　利用者は、本サービスに関連して知り得た情報のうち、当社と利用者との間での連絡内容、他の利用者その他当社の取引先又は顧客に関する情報、当社の営業上の情報、財務状況、当社が秘密と定めて開示した情報その他秘密として取り扱うことが合理的な情報について、当社の事前の書面による同意がある場合を除き、秘密に取り扱うものとします。</p>\n" +
            "                    <p><br />第１５条（本契約の有効期間）<br />　１　本契約の有効期間は、導入企業及び当社が第３条所定の申込を承諾した日から起算して１年間とします。ただし、利用者又は当社から特に申出のない場合に限り、有効期間満了日の翌日から１年間継続されるものとし、以後も同様とします。</p>\n" +
            "                    <p>　２　退職その他利用者が導入企業の従業員としての地位を失った場合、本サービスが廃止された場合、又は当社と導入企業との間の本サービスの導入に関する契約が何らかの理由で終了した場合には、本契約は当然に終了するものとします。</p>\n" +
            "                    <p><br />第１６条（存続条項）<br />　　本契約が終了した場合でも、第４条第４項第２文及び同条第６項第２文、第７条第２項、第１０条、第１２条、第１４条、本条、第１８条、並びに第１９条の規定は、なおその効力を有するものとする。</p>\n" +
            "                    <p><br />第１７条（規約の変更）<br />　１　当社は、必要と判断した場合には、予告なしに本規約を変更することがあります。なお、当社が本規約を変更した場合には、当社は、本規約の変更内容、変更の効力発生日等を利用者に通知するものとします。</p>\n" +
            "                    <p>　２　前項の通知は、導入企業を介して行うことがあるほか、当社の任意の方法によるものとします。</p>\n" +
            "                    <p>　３　第１項の通知後に利用者が本サービスを利用した場合には、利用者は変更後の本規約の内容を承諾したものとみなします。</p>\n" +
            "                    <p><br />第１８条（分離可能性）<br />　　本規約のいずれかの条項又はその一部が無効又は執行不能と判断された場合であっても、その他の部分は継続して完全に効力を有するものとし、当社及び利用者は、当該無効若しくは執行不能の条項若しくは部分を適法若しくは執行力を持たせるために必要な範囲で修正し、又は当該無効若しくは執行不能な条項又は部分の趣旨に最も近くなるよう合理的な解釈を加えて適用するものとします。</p>\n" +
            "                    <p><br />第１９条（準拠法及び管轄）<br />　　本規約は、日本国の法令に準拠し、日本国の法令に基づき解釈されるものとします。本規約に関する一切の紛争については、当社の本店所在地を管轄する裁判所を専属的合意管轄裁判所とします。</p>\n" +
            "                    <p style=\"text-align:right\">以上</p>\n" +
            "                </div>\n" +
            "            </body>\n" +
            "</html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_term_service);
        findView();
        initData();
        initEvent();
    }

    /**
     * Finds a view that was identified by the id attribute from the XML
     */
    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.title_textView);
        mAgreeCheckBox = (CheckBox) findViewById(R.id.user_term_service_agree_term_using_checkBox);
        mAgreeTermLayout = findViewById(R.id.user_term_service_agree_term_layout);
        mAgreeButton = (Button) findViewById(R.id.user_term_service_agree_term_button);
        mBackLayout = findViewById(R.id.user_term_service_back_layout);
        mTermAndConditionWebView = (WebView) findViewById(R.id.user_term_service_term_using_webView);
    }

    /**
     * Initialize
     */
    private void initData() {
        if (getIntent() != null){
            mNameOfUser = getIntent().getStringExtra(EniConstant.NAME_KEY);
        }

        mTitleTextView.setText(getString(R.string.user_term_service_title));
        mAgreeTermLayout.setEnabled(false);
        mAgreeButton.setEnabled(false);
        String youtContentStr = String.valueOf(Html
                .fromHtml("<![CDATA[<body>"
                        + htmlText
                        + "</body>]]>"));
        WebSettings webSettings = mTermAndConditionWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        mTermAndConditionWebView.loadData(youtContentStr, "text/html; charset=UTF-8", null);
    }

    /**
     * Handle button listener
     */
    private void initEvent() {
        mBackLayout.setOnClickListener(this);
        mAgreeTermLayout.setOnClickListener(this);

        mAgreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAgreeTermLayout.setEnabled(true);
                    mAgreeButton.setEnabled(true);
                } else {
                    mAgreeTermLayout.setEnabled(false);
                    mAgreeButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_term_service_agree_term_layout:
                Intent intent = new Intent(TermServiceActivity.this, AccountConfirmationActivity.class);
                intent.putExtra(EniConstant.NAME_KEY, mNameOfUser);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
                break;
            case R.id.user_term_service_back_layout:
                finish();
                startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
                break;
            default:
                break;
        }
    }
}
