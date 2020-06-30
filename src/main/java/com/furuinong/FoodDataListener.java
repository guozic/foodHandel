package com.furuinong;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.*;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
// 有个很重要的点 FoodListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class FoodDataListener extends AnalysisEventListener<Food> {

    Map<String, List<Food>> map = new HashMap<String, List<Food>>();

    String sheetName = "总表";
    List<Food> list = new ArrayList<Food>();

    String sheetName1 = "牛肉";
    String nameList1 = "牛里脊肉,牛腩,牛肉,黄牛里脊肉,牛里脊,牛里脊肉,牛百叶,牛肚,牛骨头,好牛肉,牛棒骨,牛后腿,牛腱肉,牛心,牛口条,牛骨,牛肉（里脊）,牛蹄,生牛腩,牛霖,牛骨头 ,牛肉末,生牛肚,生牛舌,牛腿肉";
    String[] split1 = nameList1.split(",");
    List<Food> list1 = new ArrayList<Food>();

    String sheetName2 = "特菜";
    String nameList2 = "鲜虫草花,兰花,洋兰花,大香芋,心里美,青萝卜,小黄瓜,法香,乳黄瓜,小土豆,薄荷叶,罗马红生菜,大叶,芦笋,水果黄瓜,彩红椒,秋葵,洋兰,三色堇,柿子椒（彩椒）,酸膜,大青萝卜,干葱,红广椒,水果萝卜,苏籽叶（紫苏）,五彩小花,小黄土豆,柠檬叶,松针,鲜薄荷叶,香茅,香芋,大红圆椒,红彩椒,红葱头,黄彩椒,韭菜花,葵叶,青彩椒,紫薯,红水果萝卜,黄金瓜,七彩菊,七彩小花,青萝卜（大）,鲜沙姜,鲜香茅,黄瓜花,酸模叶,新鲜小土豆,红圆椒,去皮芋头,迷迭香（新鲜）,七彩花,小红皮水果萝卜,羊齿苋,韭黄,彩椒,红叶生菜,苦菊,薄荷,杨兰花,圆红椒";
    String[] split2 = nameList2.split(",");
    List<Food> list2 = new ArrayList<Food>();

    String sheetName3 = "猪肉";
    String nameList3 = "猪脊骨,猪肉皮,生猪大肠,整方肥五花肉,去皮五花肉沫,里脊肉,前夹肉,前夹瘦肉,去皮五花肉,瘦肉,五花肉,鲜猪肝,新鲜猪蹄髈,优质带皮五花肉,猪肚,猪肝,猪骨,猪肋排,精五花肉,肉末,新鲜猪耳朵,猪排骨,猪腿肉,,生大肠头,猪里脊肉,排骨,猪瘦肉,猪脆骨,猪耳朵,猪脚,猪肉末,猪腰,肉片,猪骨头,猪皮,夹层五花肉（精五花）,前夹肉沫,生猪板油,猪棒骨,猪肥瘦肉,猪前肘,去皮猪前夹肉,小直排,猪肥膘肉,前蹄髈,猪月牙骨,夹心五花肉,肉沫,猪筒骨,猪腰子,肥瘦肉,后腿肉沫,五花肉（偏瘦）,猪筒子骨,肉丝,新鲜排骨,五花肉沫,猪前夹瘦肉,猪手,猪心,猪直排,猪肥膘,猪肋条,猪里脊,猪前夹肉,好五花肉,筒子骨,腰花,猪排,猪瘦肉（里脊肉）,猪臀尖,猪油,生肥肠,直猪排,猪肩肉,肥肠,前夹肉末,去皮前夹,鲜猪皮,猪肉碎,新鲜筒子骨,肉馅,生大肠,鲜猪肚,猪五花肉,猪板油,猪肚 个,鲜猪板油,优质肥膘肉,猪蹄,肥五花肉,双层五花肉,筒子骨（剁小）,五花肉（馅）,猪血";
    String[] split3 = nameList3.split(",");
    List<Food> list3 = new ArrayList<Food>();

    String sheetName4 = "青菜";
    String nameList4 = "青菜：红杭椒,红小米辣,黄豆芽,绿豆芽,毛豆,藕带,杭椒,青杭椒,青椒,青蒜,生菜,甜蜜豆,娃娃菜,西红柿,西兰花,大葱,番茄（大）,青牛角椒,红牛角椒,南瓜,土芹菜,鲜小米辣,包菜,花菜,玉米棒,小葱,香菜,西芹,芹菜,韭菜,豆芽,大番茄,茄子（烧烤大茄子）,四季豆,本地生菜,小白菜菜心,小白菜,小米椒,玉米,苦瓜,铁棍山药,香葱,韭菜（烧烤）,红椒,鲜红椒,蒜苗,大蒜叶,茄子（粗）,藕,玉米（烧烤大玉米）,黄瓜,玉米（甜）,小白菜心,生姜,大蒜头,大蒜,冬瓜,油麦菜,大白菜,嫩黄瓜,青皮苦瓜,上海青,莴笋,小米辣,新鲜藕,新鲜甜玉米棒,甜玉米,菜心,红苕,空心菜,指天骄,葱,小香葱,长豆角,大莲藕,青菜,小叶韭菜,苋菜,红薯,小红薯,丝瓜,甜玉米棒,莴苣,西蓝花,西蓝花 ,薄皮青椒,茄子,长茄子,蓝莓,草莓,奥利奥饼干116g,薄荷叶,红醋栗,核桃,红樱桃罐头,蛋挞皮（黑昆）,芒果果泥,吉利丁片,安佳淡奶油,安佳奶油奶酪,马斯卡彭,咖啡粉,咖啡力娇酒,手指饼干,布丁杯,小苏打,火腿肠,丘比沙拉酱,芒果,清净园水饴,莲藕,山药,土韭菜,竹叶菜梗,苕尖,嫩藕带,瓠子,菠菜,荷兰芹,韭黄,螺丝椒,嫩藕,新鲜毛豆米,芝麻菜,豆角,烧烤茄子,指天椒,芜湖青椒（薄皮）,竹叶菜,花生芽,青小米椒,鲜豌豆,京葱,小冬瓜,小白菜（小颗）,烧烤玉米,马齿汗,烧烤玉米（大玉米）,蒜叶,淮山药,小青菜心,白长豆角,毛豆（剪好）,青小米辣,烧烤大茄子,烧烤直黄瓜,小白菜（小颗子）,烧烤大玉米,老南瓜";
    String[] split4 = nameList4.split(",");
    List<Food> list4 = new ArrayList<Food>();

    String sheetName5 = "豆制品";
    String nameList5 = "豆制品： 白豆腐,豆腐,薄皮干子,臭豆腐,千张,香干,,熏香干（酱油干）,老豆腐,千张结,黑臭豆腐,干子,熏香干,面筋卷,豆腐泡,板豆腐,臭豆干,千张 ,柴火豆腐,好白豆腐,嫩豆腐,厚香干,湖南熏干,臭干子,皮蛋,营养豆腐,豆腐块,嫩豆腐块,水豆腐,（方）大块嫩豆腐,大方块嫩豆腐,薄皮千张,压缩白干,千叶豆腐,千页豆腐,薄千张";
    String[] split5 = nameList5.split(",");
    List<Food> list5 = new ArrayList<Food>();

    String sheetName6 = "菌类";
    String nameList6 = "菌类：鲜香菇,杏鲍菇,香菇,白菇,平菇,金针菇,新鲜香菇,口蘑,口蘑（小）,松茸,蘑菇,海鲜菇,姬菇,鲜茶树菇,蘑菇（平菇）,菌王菇,冰鲜板栗";
    String[] split6 = nameList6.split(",");
    List<Food> list6 = new ArrayList<Food>();

    String sheetName7 = "鲜鱼";
    String nameList7 = "鲜鱼：鲫鱼2条,鲫鱼16条,武昌鱼26条,大白刁,鲤鱼,鳙鱼头,草鱼,黑鱼,鲫鱼,武昌鱼,鲜鲈鱼,草鱼（1.5斤/条）,草鱼（2斤/条）,鲫鱼（0.6斤/条）,鲜野生小鲫鱼,黄骨鱼,鲜鲈鱼（1.3斤/条）,鲶鱼,草鱼（2斤条）,,鲈鱼（1.3斤/条）,草鱼2条,草鱼尾,江鲶,鱼嘴,草鱼（2.5斤/条）,胖头鱼尾,鱼泡,鲫鱼（0.5斤/条）,鲢鱼尾（35个）,草鱼（3斤/条）,鲫鱼（35条）（0.6斤/条）,鲤鱼,武昌鱼（1.2斤/条）,新鲜草鱼（杀好）（3斤/条）,花鲢白尾,草鱼（1条）,大鱼头（1个）,小黄骨鱼,小鲳鱼,草鱼（2条）,黑鱼（1.5斤/条）,草鱼（杀好）,草鱼1条,武昌鱼（1条）,罗非鱼（鲫鱼）,鲜活鲈鱼,财鱼（2斤/条）,鲢鱼身,武昌鱼36条（0.8斤/条）,武昌鱼33条（0.8斤/条）,武昌鱼48条,鲫鱼（6两/条）";
    String[] split7 = nameList7.split(",");
    List<Food> list7 = new ArrayList<Food>();


    String sheetName8 = "冻货";
    String nameList8 = "冻货：冻仔鸭10只,生鸭肠,咸鸭蛋黄,冻猪脚,鸭头,虾仁,青口,大鸡爪,鸡爪,小馒头,墨鱼仔,鱿鱼须,3140虾,蟹柳,冻金枪鱼,鲜三文鱼,鸡脆骨,方火腿（大）,烟熏鸡肉,烟熏三文鱼,鸡翅中,鸡中翅,面筋串,牛油（牧歌）,秋刀鱼串,小黄鱼串,小鸡腿,鸭边腿,鸭爪,鱿鱼串,好代装牛肉片,雪花排条,小米粑,大凤爪（大号）,鲜虾仁,水发干贝,水发海参,水发口蘑,鸡翅根,整鸡翅,鸡架,鸡软骨,鸡小腿,鹅肝,黑虎虾,鸡大腿（排腿）,金华火腿,牛百叶,鸡脯肉,冻仔鸡,光老母鸡（冻仔鸡）,小鲳鱼,鲜鱿鱼,青虾仁,牛仔骨,三明治火腿肠,日本豆腐,毛肚,鸡胗,鸭锁骨,水发鱿鱼,八爪鱼,空心土豆球,大鸡腿,鸭头（新鲜）,开花肠,蟹柳240g,水发猪蹄筋,琵琶腿,大虾仁,鸭翅,鸭头（新）,冷冻鱿鱼串,水饺（2斤装）,风干小鲫鱼（5斤装）,风干小刁子鱼,蒙古肉,鸡肉,鸡胸肉,鸭血,鸡油,小剥皮鱼,青口贝,冻仔鸡10只,罗非鱼,冰鲜小黄鱼,三文治大火腿（2kg）,手撕饼,去骨鸭掌,小黄鱼,银鳕鱼,鲜墨鱼,薯条,水妈妈牌越南春卷皮（薄）,三文治火腿,带皮牛肉,咸蛋黄,肥牛肉片,鸡后腿,光鸭,冻鸭,老蔡带皮牛肉,撒尿牛丸,夏星台式鸡排,热狗肠,长条方火腿,粽子（迷你）,冻鸭掌（5斤装）,冰鲜蹄花,袋装藕元,带鱼,鸭蛋黄,冻仔鸭,海参,蒜香骨,半边鸭,小油条,粽子,汤圆,西米,瘦肉炸串,鸭脖子,肉丸,鱼丸,水发牛蹄筋,仔鸡,鸡边腿,夹馍,扣肉,目鱼花,面筋,秋刀鱼,苏阿姨小汤圆,小汤圆";
    String[] split8 = nameList8.split(",");
    List<Food> list8 = new ArrayList<Food>();

    String sheetName9 = "干调";
    String nameList9 = "干调：李锦记黄豆酱（大瓶）,李锦记黄豆酱（小瓶）,六必居干葱酱,馒头改良剂,安琪酵母500克,雀巢炼乳,天麻,腐乳汁,黄豆,保宁醋,小苏打,葡萄干,粘米粉,黑椒汁,味精粉末,鹰栗粉,籼米粉,白鹤澄面,太太乐鸡粉,高达椰汁,张裕白兰地,草莓酱,大桥鲜味王,淡奶油,炼乳,麦芽糖,防潮糖粉,抹茶粉,蜂蜜,红油,核桃仁,花椒粉,辣妹子,辣油,金奖白兰地,蓝橙力娇酒,紫罗兰娇酒,蜜瓜力娇酒,绵白糖,杏仁粉,杏仁片,浓缩橙汁,胖子麻辣鱼,三明治火腿,三文治方火腿,五得利五星特精粉,香脆椒,香辣酥,福牌黑胡椒,1883香草糖浆,安佳黄油,洛神花,蝶豆花,红姜丝,金桔柠檬汁,芒果酱,美味汁,熟黄豆粉,炸鸡藤椒腌料,盆栽,鲍汁,鸡汁笋,麻辣香膏,肉松,橙子浓缩汁,三文治火腿,鸭骨髓膏,云梦鱼面,锡纸（大）,大粗盐,干茶树菇,高粱粉,小米面,干红薯粉条,鲜虫草花（小袋）,槟榔,肉桂,千里香,袋装银杏,甘草,干灯笼椒,荆沙辣椒酱,蔓越莓干,嫩肉粉,柠檬黄,胖子鱼（麻辣鱼）,日落黄,肉类腌制料,三洋糕粉,食粉（双斧）,酥脆剂,虾籽,鲜虾酱,香水鱼调料,香叶,冬瓜糖,茄汁焗豆,冬笋,泡红椒,三象粘米粉,苕粉面,粽叶,甘山楂,八芷,良姜,蓝莓酱,淡奶油（安佳）,奶油奶酪（安佳）,幼砂糖,罗汉果,虾皮,安佳淡奶（安佳）,红樱桃（小）,珍珠笋,墨西哥切片辣椒,明门卷,竹轮卷,鱼子酱（黄色）,罗望子酸角膏,金拓,慕斯盒,冰冻鲈鱼块（剔骨）,百香果果茸,去皮核桃仁,腰果,鲜青花椒,龙口粉丝（大包）,黑旗辣肉松,干豆角,虫草花,瑶柱（干贝）,红米,莲子,西湖莲子,莲蓉,泡打粉（大包装）,松仁,阿胶枣,海南朝天椒（辣）,金钩,蚕蛹,肥叉烧,干笋衣,紫菜苔,活塞式粽子竹筒,当归,梅干菜,芥末,脆米粒（红色）（红曲米）,寿司海苔,粽子叶,琼脂,美极鲜,斧头牌食粉,MG-8肉香麦芽粉,鸭霸王,鸭王魔香膏,高倍鲜味素,樱粟回味高,卤味增香膏,牛骨髓膏,澄面,海苔皮,无盐海米500g,广东米酒,鲜冬笋,白糖,冬菜,红薯生粉,干贝,干海带,澳宴奇鲜香王,羊肉精粉,黄姜粉,辣椒粉,红曲米,干腐竹,叉烧酱,常冠边桃,米酒,帕玛臣芝士粉,汤圆粉,三花淡奶,沙红,牛肉酱,冬笋丝,味好美藤椒腌料,味美仕香辛料粉,黄芥末膏,卡夫芝士粉,开心果,橄榄包菜,铁板烧酱,鹅肝酱,寿司海苔片,韩式辣椒酱,罗汉笋,白米粉,碳烤笋,白酱油,糙米,佬米酒,绿豆,沙姜,小米,腐竹,清水笋丝（大包）,大红浙醋,花生酱,澳宴奇（鲜味王）,橄榄油,白兰地,蕨根粉,咖喱粉,卡夫奇妙酱,辣鲜露,椰浆,带壳花生,恩施玉露,香灵草,水发鲜笋丝,东古一品鲜,房县小花菇（干）,枸杞,糯米纸,白泡椒,笋丝,超级回味王,吉士粉,土豆粉,安佳淡奶油,安佳奶油奶酪,豆沙,海苔片,红豆,烤盘油纸,绿豆馅,美乐香辣酱,丘比沙拉酱,糖果彩针,提子干,味香素,小黄油,油纸,朱师傅巧克力豆,海鲜酱,味精,干捞粉丝,蜜枣,粽子叶（4包）,干粽叶,香脆辣椒,三角豆,饴糖,干黄花菜,锅巴,白寇,酒鬼花生,熟花生,沙参,虫草（大）,枸杞（宁夏）,党参,红油腐乳,虾米,栀子,黄飞宏脆椒,干红椒,细辣椒王,干梅干菜,糯米,红枣,口味堂坛宗鱼头酱（好福记鱼头酱椒）,八角,芝麻酱,味川神厨牛肉膏,干香菇,海南朝天椒,红苕生粉,姜黄粉,香砂仁,干辣椒王（辣）,麻辣鲜（大）,油条专用粉（50斤）,上品鲜排骨味,李锦记海鲜酱,李锦记香辣酱,香辣酱";
    String[] split9 = nameList9.split(",");
    List<Food> list9 = new ArrayList<Food>();

    String sheetName10 = "粉面";
    String nameList10 = "粉面：生小面,散装老米酒,散装老米酒,熟热干面,宽粉,鹌鹑蛋,生热干面,生汤面,熟黑芝麻粉,黑芝麻,细桂林米粉,热干面（生）,汤面（生）,散装芝麻酱,河粉,凉皮,凉面,生热干面（粗）,年糕,黑芝麻仁,鲜河粉,生细凉面,小面,干豌豆,馓子,黑米,生热干面(粗),土豆粉,红豆,细面条,春卷皮,,混沌皮,皮蛋,松花蛋,细米粉,热干面,韭菜叶碱面,生碱水面,粉(河粉),馄饨皮,米粉,宽米粉,咸蛋,咸鸭蛋,散称芝麻酱,生凉面,生宽碱水面,干米粉,沙河粉,小黄米,宽面,鸡蛋,散装米酒,小米";
    String[] split10 = nameList10.split(",");
    List<Food> list10 = new ArrayList<Food>();

    String sheetName11 = "禽类";
    String nameList11 = "禽类:光鲜麻鸭,麻鸭,白条鸡,白条鸭,乌鸡,麻鸭,杀好公鸡,白条鸡,白条鸭,土麻鸭（1.8-2斤/只）,光仔鸡（2斤/只）,仔鸡（2斤/只）,仔鸡10只（2斤/只）,仔鸭,鸡肠,鸡肝,鸡心,野兔,乳鸽,三黄鸡,土鸡,仔鸡,仔鸡（活鸡）,麻鸭,黑脚鸡,鸽子";
    String[] split11 = nameList11.split(",");
    List<Food> list11 = new ArrayList<Food>();

    String sheetName12 = "水果";
    String nameList12 = "水果：小青桔,樱桃番茄,橙子,蓝莓,芒果,黄柠檬,柠檬,新鲜柠檬,苹果,奇异果,香蕉,雪梨,大青芒果,凤梨（带皮）,火龙果,牛油果,圣女果,西瓜,无籽西瓜,马蹄,鲜柠檬,西瓜（厚皮雕刻用）,梨子,带枝千禧果,车厘子,樱桃,大芒果,皇冠梨,金桔,青木瓜,水果番茄,鲜山楂,小青桔,菠萝,桃子,红心火龙果,去皮凤梨,小西瓜,油桃,青芭乐,白心火龙果,小哈密瓜,鲜车厘子,熟牛油果,榴莲,,草莓（换车厘子）,大橙子,哈密瓜,青桔,大西瓜（8斤）,小番茄,红提,猕猴桃,香橙,黄小番茄,青金桔,大台芒,鲜樱桃,青葡萄,木瓜,熟青芒果,水晶葡萄,龙眼,厚皮橙子";
    String[] split12 = nameList12.split(",");
    List<Food> list12 = new ArrayList<Food>();

    String sheetName13 = "酱菜";
    String nameList13 = "酱菜：热干面专用萝卜丁,萝卜干,青豆,甜青豆,青豆米,榨菜丝,酸豆角,酸豆角（切丁的),黑大头菜,酸萝卜,外婆菜,榨菜头,宜宾芽菜,酸菜,榨菜,酸菜 袋,泡包菜,热干面专用萝卜丁,雪菜,酸豆角（切小丁）";
    String[] split13 = nameList13.split(",");
    List<Food> list13 = new ArrayList<Food>();

    String sheetName14 = "鲜花";
    String nameList14 = "鲜花：玫瑰花,白色玫瑰,绿色小雏菊,粉色小雏菊,紫色小雏菊,绿色洋秸梗,白色洋秸梗,透明硬围边,大红色彩带,白桔梗,香槟玫瑰,月季花,康乃馨";
    String[] split14 = nameList14.split(",");
    List<Food> list14 = new ArrayList<Food>();

    String sheetName15 = "网购";
    String nameList15 = "网购：四季春茶,盾皇红复合饮料,盾皇红西柚颗粒,盾皇荔枝饮料,粉色寒天晶球,茉莉绿茶,木瓜果酱,桂花乌龙三角茶包,黑珍珠豆,艺术吸管,樱桃蔓越莓果酱,黑糖糖浆,盾皇生姜茶,气泡水,蓝莓果酱,厚压计器,五合一咖啡粉,小椰果,乐满家红茶,金钻奶油,盾皇水蜜桃饮料,德利园磨砺饮料,高域玫瑰糖浆,雀巢纯咖啡,纯椰子粉,阿果马蹄鬼话果酱,怡酱大白兔糖浆,柳橙爆珠";
    String[] split15 = nameList15.split(",");
    List<Food> list15 = new ArrayList<Food>();

    String sheetName16 = "西点西餐";
    String nameList16 = "西点西餐：圆形金币巧克力,元宝巧克力,父亲节插牌,金色珍珠糖,金条巧克力,凤凰插件,金色蝴蝶,2cm金色球,3cm金色球,4cm金色球,大红色素,蓝色色素,黑色色素,粉红色色素,绿色色素,黄色色素,橙色色素,棕色色素,紫色色素,安佰滋蛋挞皮,奶酪柯斯达,安佳淡奶油,杏仁粉,安佳奶油奶酪,热狗,洛神花,南桥烤焙油";
    String[] split16 = nameList16.split(",");
    List<Food> list16 = new ArrayList<Food>();

    String sheetName17 = "一次性用品";
    String nameList17 = "一次性用品：一次性纸碗（小）,一次性手套,一次性筷子,餐巾纸（盒）\n";
    String[] split17 = nameList17.split(",");
    List<Food> list17 = new ArrayList<Food>();

    String sheetName18 = "自采";
    List<Food> list18 = new ArrayList<Food>();


    @Override
    public void invoke(Food data, AnalysisContext context) {
//        解析数据，不同的类型进不同的list
        String name = data.getName();
//        鲜鱼
        if (Arrays.asList(split1).contains(name)) {
            list1.add(data);
        } else if (Arrays.asList(split2).contains(name)) {
            list2.add(data);
        } else if (Arrays.asList(split3).contains(name)) {
            list3.add(data);
        } else if (Arrays.asList(split4).contains(name)) {
            list4.add(data);
        } else if (Arrays.asList(split5).contains(name)) {
            list5.add(data);
        } else if (Arrays.asList(split6).contains(name)) {
            list6.add(data);
        } else if (Arrays.asList(split7).contains(name)) {
            list7.add(data);
        } else if (Arrays.asList(split8).contains(name)) {
            list8.add(data);
        } else if (Arrays.asList(split9).contains(name)) {
            list9.add(data);
        } else if (Arrays.asList(split10).contains(name)) {
            list10.add(data);
        } else if (Arrays.asList(split11).contains(name)) {
            list11.add(data);
        } else if (Arrays.asList(split12).contains(name)) {
            list12.add(data);
        } else if (Arrays.asList(split13).contains(name)) {
            list13.add(data);
        } else if (Arrays.asList(split14).contains(name)) {
            list14.add(data);
        } else if (Arrays.asList(split15).contains(name)) {
            list15.add(data);
        } else if (Arrays.asList(split16).contains(name)) {
            list16.add(data);
        } else if (Arrays.asList(split17).contains(name)) {
            list17.add(data);
        } else {
            list18.add(data);
        }
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        map.put(sheetName, list);
        map.put(sheetName1, list1);
        map.put(sheetName2, list2);
        map.put(sheetName3, list3);
        map.put(sheetName4, list4);
        map.put(sheetName5, list5);
        map.put(sheetName6, list6);
        map.put(sheetName7, list7);
        map.put(sheetName8, list8);
        map.put(sheetName9, list9);
        map.put(sheetName10, list10);
        map.put(sheetName11, list11);
        map.put(sheetName12, list12);
        map.put(sheetName13, list13);
        map.put(sheetName14, list14);
        map.put(sheetName15, list15);
        map.put(sheetName16, list16);
        map.put(sheetName17, list17);
        map.put(sheetName18, list18);

//     根据上面的方法得到的list进行新建excel
        String fileName = "G:\\work\\excelHandle\\foodhandel\\src\\main\\resources\\0630New.xlsx";
        // 这里 指定文件
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        try {
            // 去调用写入
            Set<String> shetNameStr = map.keySet();
            int i = 0;
            for (String n : shetNameStr) {
                List<Food> foods = map.get(n);
                WriteSheet writeSheet = EasyExcel.writerSheet(i, n + i).head(Food.class).build();
                excelWriter.write(foods, writeSheet);
                i++;
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
