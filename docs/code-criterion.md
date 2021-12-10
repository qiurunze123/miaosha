### 代码规范 

    老司机一般的规范大家都知道类似驼峰,匹配一类的大家都知道具体则不再提
    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
##要求：
1.lombok-- idea插件请大家下载 ,去除setget构造方法等,阿里巴巴代码规范插件请自行下载<br>
2.**尽量将长的类名，方法名，变量名精简**<br>

          1.长的类名会使开发者不易生命该类型的变量
          2.长的方法命名会使它变得晦涩难懂
          3.长的变量名不利于代码重用，导致过长的方法链
3.**命名清晰准确**<br>

          清晰: 你要知道该命名于什么有关
          精确：你要知道该命名于什么无关
          当完成这两个目标后其他的都是多余的字符
          
4.**命名无需含有表示变量或者参数类型的单词**<br>

          nameString  请写成 name
          accountLessWindow 请写成 window
          
5.**对于集合来说，最好使用名词的复数形式来描述内容**<br>

    List<DateTime> holidayDateLists 请写成 List<DateTime> holidays
    Map<Employee,Role> employeeRoleHashMap 请写成 Map<Employee,Role> employeeRoles

6.**方法名不需要描述它的参数及参数的类型–参数列表已经说明了这些**<br>

     mergeTableCells(List<TableCell> cells) 请写成 merge(List<TableCell> cells)
     sortEventsUsingComparator(List<Event> events,Comparator<Event> comparator) 请写成 sort(List<Event> events, Comparator<Event> comparator)
     
7.**省略命名中不是用来消除歧义的单词**<br>
8.**命名只是一个表示符，只要告诉你变量在哪定义不需要吧所有的信息都塞到命名里面**<br>

     recentlyUpdatedAnnualSalesBid
     存在不是最近更新的全年销售投标么？
     存在没有被更新的最近的全年销售投标么？
     存在最近更新的非全年的销售投标么？
     存在最近更新的全年非销售的投标么？
     存在最近更新的全年销售非投标的东东吗？
     上面的任何一个问题回答是不存在，那就意味着命名中引入了无用的单词
     finalBattleMostDangerousBossMonster 请写成 boss
     weaklingFirstEncounterMonster 请写成firstMonster
     如果有一些你觉得过了，太短了，容易引起歧义，但是你可以大胆的这样做，如果在之后的开发中你觉得命名会造成冲突和不明确
     你可以填一些修饰词来完善它，反之如果一开始就是一个很长的名字，你不可能再改回来
9.**省略命名中可以从上下文获取的单词**<br>

          
          // Bad:
          class AnnualHolidaySale {
            int _annualSaleRebate;
            void promoteHolidaySale() { ... }
          }
          // Better:
          class AnnualHolidaySale {
            int _rebate;
            void promote() { ... }
          }
          实际上一个命名嵌套的层次越多，他就有更多的相关的上下文，也就更简短，换句话说一个变量的作用域越小，命名就越短
10.**省略命名中无任何意义的单词**<br>

      例如：data、state、amount、value、manager、engine、object、entity 和 instance一类的 不需要这类严肃的词语 
11.**是否可以描述出一幅画（我在装逼）**<br>

      一个好的命名能够在阅读者的脑海里面描绘出一幅图画，而降变量命名为manager 并不能象读者传达出任何有关该变量做什么的信息
      在命名时可以问一下自己，把这个单词去掉含义是不是不变？如果是，那就果断把它剔除吧
12.**终极一例**<br>

    例子:好吃的华夫饼
          
          // 好吃的比利时华夫饼
          class DeliciousBelgianWaffleObject {
            void garnishDeliciousBelgianWaffleWithStrawberryList(
                List<Strawberry> strawberryList) { ... }
          }
          首先，通过参数列表，我们可以知道方法是用来处理一个strawberry 的列表 所以可以在方法的命名中去掉
          class DeliciousBelgianWaffleObject {
              void garnishDeliciousBelgianWaffle(
                  List<Strawberry> strawberries) { ... }
          }
          除非程序中还包含不好吃的比利时华夫饼或者其它华夫饼 不然我们可以将这个无用的形容词去掉
          class WaffleObject {
            void garnishWaffle(List<Strawberry> strawberries) { ... }
          }
          
          方法是包含在waffleObject类中的 所以方法名无需waffle说明
          class WaffleObject {
            void garnish(List<Strawberry> strawberries) { ... }
          }
          很明显他是一个对象，任何事物都是一个对象，这也就是传说中的面相对象的意思，所以命名中无需带有Object
          class Waffle {
            void garnish(List<Strawberry> strawberries) { ... }
          }
13.**类名应该是名词不应该是动词,使用普遍的被大众理解的词**<br>
14.**请勿抛异常直接返回**<br>
        
        类似如下规范
        Result result=Result.build();
        boolean isChecking = redisServiceUtil.getIsCheckingOfAutomatedLoanService();
        result.setValue(isChecking);
        if (isChecking) {
            logger.info("--- isSystemChecking ---系统清算中-----");
            result.withError(ResultMsgStatus.SYSTEM_CHECKING.getMessage());
        }
        return result;

