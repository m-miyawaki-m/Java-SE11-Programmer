# Java Silver 試験対策メモ
## １．Java の基本
package、import 宣言は同一ファイル内の全てのクラスに適用される

package 宣言は必ずソースコード先頭（package より前にはコメントのみ記述できる）

java.lang パッケージは基本的なクラスがまとめられており、import 宣言が必要ない

「*」 を使っても、サブパッケージに属するクラスは import されない

import test.*とするとtest.MyClass1は import されるが test.sub.MyClass2は import されない

デフォルトパッケージ（無名パッケージ）に属するクラスは、無名パッケージに属するクラスからしかアクセスできない そもそも import を書けず、コンパイルエラーになる

static なフィールドやメソッドにアクセスする際に

import static jp.co.xxx.Sample.num
import static jp.co.xxx.Sample.print
としておけば修飾子を省略できる

static import ではなく import static

エントリーポイントのシグニチャは以下の形式

public static void main (String[] xxx)
public static void main (String... xxx)
のいずれか。

main メソッドはオーバーロードしてもOK

当然、シグニチャは違っている必要がある

javac コマンド（コンパイル）は拡張子あり、java コマンド（実行）は拡張子なし

javac Main.java
java Main

## ２．Java のデータ型の操作
プリミティブ型
データ型	値
boolean	1 bit
char	16 bit Unicode文字
byte	8 bit
short	16 bit
int	32 bit
long	64 bit
float	32 bit
double	64 bit
byte が 8 bit でそこから倍々と覚える

int - long は float - double と一緒

正負があるので注意（例：byte (-128 ~ 127) に 0b11111111 (= 255)は入らない）

数値リテラルのデフォルトは** int および double **

整数リテラル

進数	表記
10進数	123
16進数	0x1A8
8進数	0123
2進数	0b011
「 _ 」を使った数値表記

先頭末尾・記号（「 . 」「 L 」「 F 」「 0x 」... ）の前後はダメ

連続はOK

char 型に入るのは

シングルクオーテーションで囲った文字リテラル
シングルクオーテーションで囲った「\u」から始まる Unicode番号（文字リテラル）
0 ~ 65535 までの数値リテラル
のいずれか

文字列リテラル（ダブルクオーテーション囲み）はダメ

識別子に使える記号は「 _ 」および「 $ 」のみ

数字は２文字目から

null を表現するリテラルは「null」のみで「NULL」は不可

println(null) では「null」と出力される

クラスとインスタンスの関係は、コピー元とコピーの関係

## ３．演算子と判定構造の使用
代入演算子（ = や += ）は、オペランドの評価が終わってからでないと代入されない
int a = 3;
int b = a += 5; // a = 8, b = 8
「 - 」は単項演算子としても働く
int a = 10 * - 10; // a = -100
数値リテラルの互換性として、範囲大→範囲小のときは明示キャストが必要
ただし、整数リテラルに関しては範囲内ならOK
byte a = 0b10000000; // int →　byte は範囲外なら OK だが範囲外なのでコンパイルエラー
short b = 128 + 128; // short の範囲内なので int → short だが OK
int c = 2 * 3L; // long → int なのでコンパイルエラー
float d = 10.0; // double → float なのでコンパイルエラー（10.0f なら OK）
Java では boolean と数値型に互換性はない
boolean a = 1; // コンパイルエラー
byte b = true; // コンパイルエラー
関係演算子：

==
!=
>, <, >=, <=
数値以外には使えない
instanceOf
instanceOf は指定した型でインスタンスを扱えるかどうか判定する

interface A {}
class B implements A {}

---

B b = new B();
System.out.println(b instanceOf B); // true
System.out.println(b instanceOf A); // true
&& や || はショートサーキット演算子とよばれ、左オペランドの結果次第で右オペランドは評価しない
false && x++ == 1; // false, x++ は無視
false & x++ == 1; // false, x++ される
true || x++ == 1; // true, x++ は無視
true || x++ == 1; // true, x++ される
演算子の優先順位は

(), ++, -- が最優先
*, / と % は同列
「 == 」：同一性　「 equals() 」：同値性

Object クラスの equlas メソッドは同一性になっているのでオーバーライド前提

引数として Object クラスを受け取るため、引数の型を変えてしまうとオーバーライドにならない

コード中に同じ文字列リテラルが登場した場合、同じインスタンスへの参照が使いまわされる

「コンスタントプール」という仕組み

文字列リテラルはインスタンス用メモリ空間ではなく定数用メモリ空間に String インスタンスが作られる

明示的に new すれば別

String a = "test"; // 定数用メモリ空間に作られた String インスタンスへの参照
String b = "test"; // 定数用メモリ空間に作られた String インスタンスへの参照
System.out.println(a == b) // true、「同値」だけでなく「同一」となる

String c = new String("test"); // // インスタンス用メモリ空間に作られた String インスタンスへの参照
System.out.println(a == c) // false
System.out.println(a.equlas(c)) // true
else if 文は「else」と「if」の間で改行不可
if (条件式A) hoge();
else
if (条件式B) fuga();
else bar();

// これは以下と等価

if (条件式A) hoge();
else {
    if (条件式B) fuga();
    else bar();
}
switch 文の条件に入るのは

int 型以下の整数型およびそのラッパー
文字、文字列
列挙型
のみ

boolean、long はダメ

switch 文の case 値に入るのは定数のみ、即ち

リテラル
final 宣言された変数
のいずれかで変数はダメ

switch 文の default を最後に書くのは慣例で、どこでも構わない

default を最初に書いても「どの case にも当てはまらない場合」というのは変わらない

すべて default ケースにはならない

## ４．配列の作成と使用
配列型変数宣言は要素数 0 でもOK
int[] a = new int[0]; // 要素数 0 の配列オブジェクトも作れる
int[] b = new int[3];
ArrayList<Integer> c = new ArrayList<>();

System.out.println(a); // [I@677327b6
System.out.println(b); // [I@14ae5a5  ※ArrayList とは違って println しても中身は表示されない
System.out.println(c); // []
配列型変数宣言の [] はデータ型、変数名のどちらの後ろでもOK
// いずれもコンパイルOK
int[] a;
int b[];
int[][] c[];
配列型変数宣言時に、要素数は指定できない
配列型変数は配列インスタンスへの参照であり、配列インスタンス生成とは別
int[3] a; // コンパイルエラー
int b[3]; // コンパイルエラー
int c[] = new int[3]; // OK. int[3] の配列インスタンスが生成され、そこへの参照が c にセットされる
配列インスタンスの生成には、要素数を指定しなければならない
変数は OK
多次元配列の場合、１次元目だけは省略できない
int[] a = new int[3 * 5]; // ok
int x = 5;
int[] b = new int[x]; // ok
int[] c = new int[3.5]; // コンパイルエラー
int[] d = new int[]; // コンパイルエラー

int[][][] e = new int[3][4][5]; // ok
int[][][] f = new int[3][][]; // ok
int[][][] g = new int[][][5]; // コンパイルエラー
配列インスタンス生成時のデフォルト要素は決まっている

数値型：0
boolean：false
参照型：null
配列初期化演算子 {} で配列の中身を初期化出来る

int[] a = {2, 3}; // ok
int[] b = new int[]{2, 3}; // ok
int[] c = new int[2]{2, 3}; // コンパイルエラー　初期化演算子を使うときは要素数は指定しない
int[] d = {}; // 要素なしでも ok
int[] e;
e = {}; // コンパイルエラー　初期化演算子は配列型変数宣言時しか使えない
配列型変数は、親クラスの配列型変数に暗黙的に型変換できる（アップキャスト的に）
Child[] c = new Child[]{new Child(), new Child()}
Parent[] p = c; // ok
clone() メソッドで配列のコピーが出来る

ただし複製されるのは１次元目まで

多次元配列の場合、２次元目以降は同じインスタンスを参照していることになる

arraycpy() メソッドでも配列のコピーが出来る

arraycpy(src, srcStartIndex, dst, dstStartIndex, numOfElements)

５．ループ構造の使用
while 文は処理なしでもコンパイルエラーにはならない

例：while (true); // 無限ループ
for 文の初期化式では複数の変数を宣言できるが、同じ型のみ

複数文書けるのは初期化・更新式のみ

条件式は必ず１つ

for (int i = 0, j = 10; true; i++, j++) {}// ok
for (int i = 0, long j = 10; true; i++) {}// コンパイルエラー
for (int i = 0; true, i < 10; i++) {} // コンパイルエラー
for 文では条件式を省略しても ok

break を使わない限り無限ループ

拡張 for 文では参照変数をコピーしているので、参照先を変えても元の配列には影響を与えない

Sample[] array = {new Sample(1), new Sample(2), new Sample(3)};
System.out.println(array[0].getValue()); // 1
for (Sample s : array){
    s = new Sample(4);
    System.out.println(s.getValue()); // 4
}
System.out.println(array[0].getValue()); // 1

// s.setValue(4) などとすれば元の配列にも影響が出る
拡張 for 文では、順方向に、１つずつしか処理できない

ラベルを使うことで、continue や break の制御を移す箇所を自由に指定できる（本来は直近のみ）

ラベルはあらゆる箇所につけられる

６．メソッドとカプセル化の操作
可変長引数

引数の型のあとにピリオド３つ
void sample (int... num){ System.out.println(num[0]); }
可変長引数が使えるのは最後の引数のみ
クラスファイルのロード時、static フィールドやstatic メソッドは「static 領域」に配置される

それ以外の部分の定義は「ヒープ領域」に配置され、インスタンス生成のたびに読まれる

よって static なフィールドはインスタンスがなくても使える

static なメソッドから、static でないメソッド・フィールドにはアクセスできない

オーバーロードはシグニチャを別にする必要がある

シグニチャ = メソッド名 + 引数の型・順番

アクセス修飾子・引数名・戻り値の型を変えてもダメ

コンストラクタの制限

メソッド名とクラス名を揃える
戻り値型は記述できない
戻り値を記述した場合、それはコンストラクタではなくただのメソッドと解釈される
new と一緒にしか使えない
アクセス修飾子はなんでも良い（private も可）
初期化ブロック（クラス直下の{}）はコンストラクタの前に実行される

デフォルトコンストラクタは、明示的にコンストラクタを書いたときには生成されない

引数ありのコンストラクタを記述した場合、引数なしのデフォルトコンストラクタは生成されないので注意

サブクラスにおいて、super クラスのコンストラクタは必ず自身のコンストラクタの最初で呼ばれる必要がある

明示しない場合、コードの最初に super() が挿入される

そのため親クラスに super() がなければコンパイルエラーになる

別のコンストラクタを呼び出す場合は this() を用いる

これは初めに呼び出さなければならない

アクセス修飾子

修飾子	説明
public	すべてOK
protected	サブクラス、もしくは同一パッケージのクラスのみ
なし	同一パッケージのクラスのみ
private	クラス内からのみ
７．継承の操作
以下の２つは、サブクラスへは引き継がれない

コンストラクタ
private なフィールド・メソッド
インタフェースの特徴

インスタンス化できない
メソッドは public のみ、省略しても public
実装は持てない（{}は記述できない）
フィールドは以下に限り持てる
final （ = 変更のない定数）
static （ = インスタンス化しなくても使える）
実装（実現）は implements 、多重実現が可能
extends でインタフェースを継承したインタフェースを作れる
具象クラスはインタフェースの抽象メソッドをすべて実現しなければならない
抽象クラスはその必要はない
抽象クラスの特徴

インスタンス化できない
インターフェースと具象クラスのあいのこ
すなわち、抽象メソッドと具象メソッドの両方を持て、フィールドも定義できる
抽象クラスを継承した具象クラスはすべての抽象メソッドを実装しなければならない
抽象クラスの抽象メソッドは、public である必要はない
オーバーライドのルール

戻り値は同じか、サブクラス（ = 共変戻り値）
シグニチャが同じ
アクセス修飾子は同じか、より緩いもの
interface の 抽象メソッドは public なので、具象クラスもすべて public にしなければならない
サブクラスは、スーパークラスとサブクラスのインスタンスを両方持ち、見かけ上１つのインスタンスと考える

従ってサブクラスのコンストラクタでは、スーパークラスのコンストラクタも先に呼ばないといけない
スーパークラスとサブクラスに同名のフィールドが定義されている場合、両方別々に保持していることになる
（ただし、オーバーライドしたメソッドは、インスタンスとして１つと考える）
同名のフィールドを参照した場合、宣言した変数の型によってどちらを使うかが決まる
メソッドから参照した場合は、そのメソッドが宣言されたクラスのフィールドを使う
class Parent {
    String val = "P";
    public String getVal1(){
        return val;
    }
    public String getVal2(){
        return val;
    }
}

class Child extends Parent {
    String val = "C";
    public String getVal2(){
        return val;
    }
}

class Main {
    public static void main (String args[]){
        Parent p = new Parent();
        Parent c = new Child();
        System.out.println(p.val); // P
        System.out.println(c.val); // Parent として宣言しているので P
        System.out.println(p.getVal1()); // P
        System.out.println(c.getVal1()); // Parent で宣言したメソッドなので P
        System.out.println(p.getVal2()); // P
        System.out.println(c.getVal2()); // オーバーライドされているので C
    }
}
実現について
interface A { public void abst(); }
class B { public void abst(); }
class C extends B implements A{ } // ok. A の抽象メソッドを実現してるとみなされる
サブクラスのみに定義しているメソッドやフィールドは、親クラスとして扱っているときには呼び出せない

キャスト = 「コンパイラへの互換性への保証」

互換性のないものへキャストを書けば、コンパイル時にエラーになる
(String) 1 // コンパイルエラー
継承関係にないクラス同士でキャストしてもコンパイルエラー
継承関係にあるクラス同士のキャストをする場合
アップキャスト（サブクラスからスーパークラスへのキャスト）は暗黙で問題ない
extends によってコンパイラが自分で判断できるから
ダウンキャストは、キャスト式で明示しなければならない
キャスト対象のインスタンスがどの型なのか見えないから
もし、キャスト不可（スーパークラスのインスタンスをサブクラスにキャストしようとしていた）であれば
実行時にエラーになる
class Parent {}
class Child extends Parent{ }

class Main{
    public static void main(String args[]){
        Parent c = new Child();
        Parent p = new Parent();

        Child c_ = (Child) c; // ok
        Child p_ = (Child) p; // コンパイルは通るが、実行時に ClassCastException
    }
}
ローカル変数同士は、同一スコープ内で名前がかぶってはいけない
フィールドとローカル変数はかぶってOK。this をつけて区別する
int num = 10;
if (num < 11){
    int num = 20; // コンパイルエラー
    int value = 100; // スコープが違うのでOK
}
int value = 200;
ローカル変数は、プログラマが明示的に初期化しなければならない

## ８．例外の処理
try-catch-finally 構文

順番を変えることはできない
try
必須・１つのみ
catch
省略可・複数記述可能
finally
省略可・１つのみ
catch、finally ともに省略はできない
catch の中で return していても、finally 文が実行されてから return する

catch 文でも finally 文でも return をしている場合、finally の return で上書かれる

return 専用変数があるイメージなので、finally で return した変数を書き換えるのでは上書かない

public static int sample1(){
    try {
        throw new Exception();
    } catch (Exception e){
        return 0;
    } finally {
        return 1;
    }
}

public static int sample2(){
    int val;

    try {
        throw new Exception();
    } catch (Exception e){
        val = 0;
        return val;
    } finally {
        val = 1;
    }
}

public static void main (String args[]){
    System.out.println(sample1()); // 1
    System.out.println(sample2()); // 0
}
Throwable

Error
プログラムからは回復不可能なトラブル
try-catch や throws 宣言はしてもしなくても良い
Exception
検査例外（ = RunTimeException 以外の Exception）
非検査例外（ = RunTimeException）
try catch や throws 宣言はしてもしなくても良い
Exception が生じる場合は

try catch で処理をする
throws をメソッドで宣言する
のいずれかをしなければならない。throws 宣言されたメソッドを使う側でも上記は適用される
main メソットでも、上の２つのいずれかが必要

Error

OutOfMemoryError
クラス定義やインスタンスを保持するヒープ領域が不足した場合
NoClassFoundError
実行対象のクラスファイルがない場合
StackOverflowError
メソッドの実行に必要な情報を配置するスタック領域が不足した場合
無限再帰呼び出しなど
ExceptionInInitializerError
static イニシャライザ（static 変数の初期化などを行う）で例外が発生した場合
例外を通知する相手がいないのでこのエラーを出してプログラムが強制終了する
AssertionError
アサーションの条件式に合致しないものがある
InternalError
JVM 内での何らかのエラー
VirtualMachineError
OutOfMemoryError、StackOverflowError、InternalError の親クラス
JVM が壊れたなど
RunTimeException

範囲外系
ArrayIndexOutOfBoundsException
配列での範囲外アクセス
IndexOutOfBoundsException
ArrayList での範囲外アクセス
StringIndexOutOfBoundsException
string.charAt() での範囲外アクセス
Illegal 系
IllegalArgumentException
引数の事前条件が守られていない
IllegalStateException
準備が終わってないなど
その他
ClassCastException
NumberFormatException
NullPointerException
SecurityException
Java API の主要なクラスの操作
String
String は immutable

初期化したら、フィールドを書き換えることは出来ない

String は CharSequence のサブクラス

String の基本メソッド

replaceAll(".", "hoge")
正規表現での置き換え
replace("src", "dst")
単純な置き換え（複数登場する場合もすべて）
charAt(index)
indexOf("abc")
なければ -1
subString(start, end)
subString(start)
trim()
スペース、\t（タブ文字）、\n\r（改行）
文字列の前後のみで、文字列中は除去しない
startsWith("a")
endsWith("a")
split("\\w\\s")
正規表現で分割
\w：単語構成文字
\s：空白文字
\d：数字
大文字は逆
concat("a")
連結する
文字列の連結

String a = "30" + 5; // 305
String b = "30" + 5 + 8; // = "305" + 8 = "3058"
String c = 5 + 8 + "30"; // = 13 + "30" = "1330"

String d = "hoge" + null; // = "hogenull"
StringBuilder はデフォルトで 16文字分のバッファを持つ
コンストラクタでバッファを指定できる
StringBuilder sb = new StringBuilder("abc");
System.out.println(sb.capacity()); // 3 + 16 = 19

StringBuilder sb2 = new StringBuilder(5);
System.out.println(sb2.capacity()); // 5
StringBuilder のメソッド

append(x)
x には、プリミティブ型を全て入れることができる
10 なら "10"、true なら "true"
insert(index, "hoge")
delete(start, end)
deleteCharAt(index)
reverse()
replace(start, end, "hoge")
subString(start, end)
String
subSequence(start, end)
CharSequence
toString()
内部の文字列を返す
parseXXX

Integer.parseInt("123")
文字列からプリミティブへ
valueOf

Integer.valueOf("123")
文字列からラッパークラスへ
これらは大文字小文字を区別しない

ラムダ式
ラムダ式
実装が必要なメソッドを１つだけ持つインタフェースを「関数型インタフェース」と呼ぶ
ラムダ式でメソッドを実装できる
// 関数型インタフェース
interface Algorithm1 { 
    void perform(String name);
}

interface Algorithm2 {
    String perform(String name);
}

---

Algorithm1 a = (String name) -> { System.out.println(name); }
Algorithm1 b = (name) -> { System.out.println(name); } // 引数の型は省略可能
Algorithm1 c = name -> System.out.println(name); // 引数は１つなら () 省略可能、メソッドは１つなら {} 省略可能

Algorithm2 d = name -> { return "hello " + name + " !"; }
Algorithm2 e = name -> "hello " + name + " !"; // 戻り値が必要かつ {} を省略する際は、return 記述不可
Algorithm2 e = name -> return "hello " + name + " !"; // コンパイルエラー

メソッド内で宣言したローカル変数と同じ名前の変数をラムダ式の引数名として使うことは出来ない（同一スコープ）

ラムダ式外で宣言されたローカル変数にラムダ式内からアクセスするには

実質的に final な変数（変更されることがない変数）でなければならない

標準関数型インタフェース

Consumer
void accept(T)
Supplier
T get()
Predicate
boolean test(T)
Functions
R apply(T)
DateTime
LocalDate

mutable な Clendar に対して immutable
月が１始まり
LocalDate.of(2018, 12, 24)
LocalDate.parse("2015-01-01")
基本的な書式はyyyy-MM-dd
存在しない日付は DataTimeException
LocalDate.of(2017, 1, 32) // DateTimeException
LocalTime

immutable
24 時間
plusHours(10)
LocalDateTime

LocalDate と LocalTime を合わせた
LocalDateTime.of(2018, 12, 24, 18, 0, 0);
Duration

時刻の差を扱う
Duration d = Duration.between(start, end)
Period

日付の差を扱う
Period p = Period.between(start, end)
Period p = localDate.until(target)
DateTimeFormatter

ISO_DATE_TIMEが一番標準的な？
localDateTime.format( DateTimeFormatter.ISO_DATE_TIME ) // 2018-08-31T00:00:00
ArrayList
ArrayList

スレッドセーフでない
Vector はスレッドセーフ
add(element)
add(index, element)
set(index, element)
remove(element)
equals() で等しい要素を検索して削除する
最初の１つのみ削除する
後ろの要素が繰り上がる
どうやら、ループで読み出しをしている最中に remove を呼び出すと、
ループの最後で remove する時以外は ConcurrentModificationException がスローされる
それが嫌であれば iterator を使う
removeIf(ラムダ式)
List や ArrayList のジェネリクスは制約を課すもので、なくても構わない（Object型 の List になる）

tips
到達不可コードがあるとコンパルエラー
// サブクラスの Exception が後ろに来る
try {} catch (Exception e) {} catch (IOException e) { /** cannot reach **/ };
// coninue の後に処理を書く
for (int i : list){
    if ( flg ) {
        continue;
        hoge(); // cannot reach
    }
}
// return 後の処理
void sample(){
    return;
    hoge(); // cannot reach
}