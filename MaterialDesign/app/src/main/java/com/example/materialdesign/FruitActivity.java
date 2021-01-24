package com.example.materialdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {
  public static final String FRUIT_NAME = "fruit_name";
  public static final String FRUIT_IMAGE_ID = "fruit_image_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fruit);
    Intent intent = getIntent();
    String fruitName = intent.getStringExtra(FRUIT_NAME);
    int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID,0);
    Toolbar toolbar = findViewById(R.id.toolBar);
    CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
    ImageView fruitImageView = findViewById(R.id.fruit_image_view);
    TextView fruitTextView = findViewById(R.id.fruit_content_text);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    collapsingToolbarLayout.setTitle(fruitName);
    Glide.with(this).load(fruitImageId).into(fruitImageView);
    String fruitContent = generateFruitContentText(fruitName);
    fruitTextView.setText(fruitContent);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private String generateFruitContentText(String fruitName) {
    String contentText = null;
    switch (fruitName) {
      case "Apple":
        contentText = "成都贵素质，酒泉称白丽。红紫夺夏藻，芬芳掩春蕙。\n" +
            "　　映日照新芳，丛林抽晚蒂。谁谓重三珠，终焉竞八桂。\n" +
            "　　不让圜丘中，粲洁华庭际。" + "\n\n" + "树下阴如屋，香枝匝地垂。吾侪携酒处，尔柰放花时。\n" +
            "　　有实儿童摘，无材匠石知。成蹊若桃李，难以并幽姿。" + "\n\n" + "俱荣上节初，独秀高秋晚。吐绿变衰园，舒红摇落苑。\n" +
            "　　不逐奇幻生，宁从吹律暄。幸同瑶华折，为君聊赠远。" + "\n\n" + "累累後棠柰，落尽风雨枝。行乐偶散步，倚杖聊纵窥。\n" +
            "　　林叶隐孤实，山鸟曾未知。物亦以晦存，悟兹身世为。";
        break;
      case "Banana":
        contentText = "香蕉弯弯的,就像一个月亮.它的颜色金金黄黄的,看上去就像一条条在发光的小船,摸上去非常光滑。\n" +
            "\n" +
            "它有一件美丽的\"外衣\",只要把\"外衣\"剥下来,那么金黄色的肉就会展在你眼前.咬一口,舌尖触到香蕉那平滑的肉,使人感到细腻而且柔软。\n" +
            "\n" +
            "香蕉先是青色的,随后变成金黄色的了,最后几乎会发光了,它不是真的会发光了,因为它太光滑了,所以像在发光。";
        break;
      case "Cherry":
        contentText =
            "华林满芳景，洛阳编阳春，未颜合运日，翠色影长津。" + "\n\n" + "为花结实自殊常，摘下盘中颗颗香。味重不容轻众口，独于寝庙荐先尝。" + "\n\n" +
                "绿葱葱，几颗樱桃叶底红。" + "\n\n" + "瑞香端合谱离骚，有子傅芳韵亦高。失却薰笼红锦被，化为矮树紫樱桃。" + "\n\n" +
                "梅子流酸溅齿牙，芭蕉分绿上窗纱。日长睡起无情思，闲看儿童捉柳花。";
        break;
      case "Grape":
        contentText = "野田生葡萄，缠绕一枝高。移来碧墀下，张王日日高。\n" +
            "分岐浩繁缛，修蔓蟠诘曲。扬翘向庭柯，意思如有属。\n" +
            "为之立长檠，布濩当轩绿。米液溉其根，理疏看渗漉。\n" +
            "繁葩组绶结，悬实珠玑蹙。马乳带轻霜，龙鳞曜初旭。\n" +
            "有客汾陰至，临堂瞪双目。自言我晋人，种此如种玉。\n" +
            "酿之成美酒，令人饮不足。为君持一斗，往取凉州牧。" + "\n\n" + "西园晚霁浮嫩凉，开尊漫摘葡萄尝。\n" +
            "满架高撑紫络索，一枝斜亸金琅珰。\n" +
            "天风飕飕叶栩栩，蝴蝶声干作晴雨。\n" +
            "神蛟清夜蛰寒潭，万片湿云飞不起。\n" +
            "石家美人金谷游，罗帏翠幕珊瑚钩。\n" +
            "玉盘新荐入华屋，珠帐高悬夜不收。\n" +
            "胜游记得当年景，清气逼人毛骨冷。\n" +
            "笑呼明镜上遥天，醉倚银床弄秋影。";
        break;
      default:
        break;
    }
    return contentText;
  }


}