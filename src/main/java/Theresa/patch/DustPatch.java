package Theresa.patch;


import Theresa.action.DustAction;
import Theresa.action.PlayCardAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.character.Theresa;
import Theresa.modcore.TheresaMod;
import Theresa.power.AbstractTheresaPower;
import Theresa.power.buff.IntoHistoryPower;
import basemod.ReflectionHacks;
import basemod.helpers.CardTags;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.ArrayList;
import java.util.Collections;

public class DustPatch {
    private static final float Y_OFFSET = 70F * Settings.scale;
    private static final float CIRCLE_DRAW_SCALE = 0.15F;
    private static final float PREVIEW_DRAW_SCALE = 0.7F;

    public static DustManager dustManager = new DustManager();

    //环绕以正面和背面构成，由于人物整体位于水平线以下，正面向下抛物线，背面向上抛物线
    //微尘卡牌的环绕drawScale为0.15，展示drawScale为0.7F，后文drawScale以DS简称
    //环绕时，宽度至少为人物Hb宽度 + DS * 卡牌宽度，若微尘较多，则取（微尘数/2F）* 2F * DS * 卡牌宽度
    //关于xScale：xScale计算 人物x坐标xP 卡牌x坐标xC 环绕直径width 满足：xScale = [(width/2)^2 - (xC-xP)^2]^(1/2) / (width/2) （环绕至背面时xScale取相反数）
    //关于yScale：yScale保持不变

    private static boolean usingXScale = false;
    private static float currentXScale = 1F;

    public static void drawPileToDust(){
        AbstractPlayer p = AbstractDungeon.player;
        AbstractCard c = p.drawPile.getTopCard();
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        c.setAngle(0.0F, true);
        c.lighten(false);
        c.drawScale = 0.12F;
        c.targetDrawScale = CIRCLE_DRAW_SCALE;
        c.triggerWhenDrawn();
        dustManager.addCard(c);
        p.drawPile.removeCard(c);

        for(AbstractPower p1 : p.powers) {
            p1.onCardDraw(c);
        }

        for(AbstractRelic r : p.relics) {
            r.onCardDraw(c);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class CardScaleField{
        public static SpireField<Float> xScale = new SpireField<>(() -> 1.0F);
        public static SpireField<Boolean> before = new SpireField<>(() -> true);
    }

    @SpirePatch(clz = SpriteBatch.class, method = "draw", paramtypez = {TextureRegion.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class})
    public static class SpriteDrawPatch{
        @SpireInsertPatch(rloc = 0, localvars = {"scaleX"})
        public static void Prefix(SpriteBatch _inst, TextureRegion region, float x, float y, float originX, float originY, float width, float height, @ByRef float[] scaleX, float scaleY, float rotation){
            if(usingXScale){
                scaleX[0] *= currentXScale;
            }
        }
    }

    @SpirePatch(clz = SpriteBatch.class, method = "draw", paramtypez = {Texture.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int.class, int.class, int.class, boolean.class, boolean.class})
    public static class SpriteDrawPatch2{
        @SpireInsertPatch(rloc = 0, localvars = {"scaleX"})
        public static void Prefix(SpriteBatch _inst, Texture texture, float x, float y, float originX, float originY, float width, float height, @ByRef float[] scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY){
            if(usingXScale){
                scaleX[0] *= currentXScale;
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderHelper", paramtypez = {SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class})
    public static class DrawScalePatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractCard _inst, SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY){
            if(dustManager.containsCard(_inst)){
                usingXScale = true;
                currentXScale = CardScaleField.xScale.get(_inst);
            }
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst, SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY){
            usingXScale = false;
            currentXScale = 1F;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
    @SpirePatch(clz = AbstractCard.class, method = "renderJokePortrait")
    public static class DrawPortraitPatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractCard _inst, SpriteBatch sb){
            if(dustManager.containsCard(_inst)){
                usingXScale = true;
                currentXScale = CardScaleField.xScale.get(_inst);
            }
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst, SpriteBatch sb){
            usingXScale = false;
            currentXScale = 1F;
        }
    }

    //更新及渲染
    @SpirePatch(clz = AbstractPlayer.class,method = "update")
    public static class UpdatePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst){
            dustManager.update();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "render")
    public static class RenderPatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer _inst, SpriteBatch sb){
            dustManager.renderAfter(sb);
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst, SpriteBatch sb){
            if(!AbstractDungeon.player.hb.hovered)
                dustManager.renderBefore(sb);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "render")
    public static class RenderPatch2{
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon _inst, SpriteBatch sb){
            if(AbstractDungeon.player.hb.hovered){
                sb.setColor(Color.WHITE.cpy());
                dustManager.renderBefore(sb);
            }
        }
    }

    private static boolean banFullDialog = false;

    @SpirePatch(clz = AbstractPlayer.class,method = "createHandIsFullDialog")
    public static class BanFullDialogPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer _inst){
            if(banFullDialog){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    //抽牌转化
    @SpirePatch(clz = DrawCardAction.class, method = "update")
    public static class ChargeDrawPatch{

        //抽牌数溢出修正 当且仅当 手牌满且抽牌堆顶不为攻击牌或技能牌时不执行
        @SpireInsertPatch(rloc = 42,localvars = {"handSizeAndDraw"})
        public static void InsertBefore(DrawCardAction _inst, @ByRef int[] handSizeAndDraw){
            if(!dustManager.isFull()){
                if(AbstractDungeon.player.hand.size()>=10){
                    if(AbstractDungeon.player.drawPile.isEmpty())
                        return;
                    AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
                    if(c.type!= AbstractCard.CardType.ATTACK && c.type != AbstractCard.CardType.SKILL)
                        return;
                    if(CardTags.hasTag(c,OtherEnum.Theresa_Darkness))
                        return;
                }
                int dustRemains = dustManager.dustUpLimit - dustManager.dustCards.size();
                handSizeAndDraw[0] += dustRemains;
                if(handSizeAndDraw[0] > 0)
                    handSizeAndDraw[0] = 0;
                banFullDialog = true;
            }
        }

        @SpireInsertPatch(rloc = 45)
        public static void InsertAfter(DrawCardAction _inst){
            banFullDialog = false;
        }

        //禁止创建抽牌满警告

        @SpireInsertPatch(rloc = 71)
        public static SpireReturn<Void> Insert(DrawCardAction _inst){
            try {
                if (!dustManager.isFull()) {
                    AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
                    if (!CardTags.hasTag(c, OtherEnum.Theresa_Darkness)) {
                        if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL) {
                            drawPileToDust();
                            if (_inst.amount == 0) {
                                ReflectionHacks.privateMethod(DrawCardAction.class, "endActionWithFollowUp").invoke(_inst);
                            }
                            return SpireReturn.Return();
                        }
                    }
                }
            }
            catch (Exception e){
                TheresaMod.logSomething("Something went wrong with CHARGE DRAW!!!");
            }
            return SpireReturn.Continue();
        }
    }

    //抵挡伤害
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class PlayerDamagePatch{
        @SpireInsertPatch(rloc = 90,localvars = {"damageAmount"})
        public static void Insert(AbstractPlayer _inst, DamageInfo info, @ByRef int[] damageAmount){
            damageAmount[0] = dustManager.blockDamage(damageAmount[0]);
        }
    }

    //战斗开始reset
    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class BattlePrePatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon _inst, SaveFile saveFile){
            dustManager.preBattle();
        }
    }

    //上半部分的hover不显示tips
    @SpirePatch(clz = AbstractPlayer.class,method = "renderPowerTips")
    public static class PowerTipsPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer _inst, SpriteBatch sb){
            float middle = _inst.hb.cY;
            if(!DustPatch.dustManager.dustCards.isEmpty() && InputHelper.mY>=middle){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }


    public static void preBattle(){
        dustManager.preBattle();
        //...
    }

    public static void postBattle(){
        dustManager.postBattle();
    }

    public static void atTurnStart(){

    }

    public static void atTurnStartPostDraw(){
        AbstractDungeon.actionManager.addToBottom(new DustAction(1));
    }

    public static class DustManager{
        //取值范围 [0,1)
        private float rotateRate = 0F;

        public int dustUpLimit = 0;
        public ArrayList<AbstractCard> dustCards = new ArrayList<>();

        public void preBattle(){
            dustCards.clear();
            if(AbstractDungeon.player instanceof Theresa)
                dustUpLimit = 3;
            else
                dustUpLimit = 0;
        }

        public void postBattle(){
            for(AbstractCard c : dustCards){
                c.fadingOut = true;
                c.targetTransparency = 0F;
            }
        }

        public void atTurnEnd(){
            for(AbstractCard c : dustCards){
                if(c instanceof AbstractTheresaCard){
                    ((AbstractTheresaCard) c).atTurnEndIfDust();
                }
            }
        }

        public int blockDamage(int damageAmount){
            ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
            for(AbstractCard c : dustCards){
                if(c instanceof AbstractTheresaCard){
                    int amt = ((AbstractTheresaCard) c).blockDamageIfDust();
                    damageAmount -= amt;
                    if(amt>0 && ((AbstractTheresaCard) c).exhaustAfterBlockDamage)
                        cardsToExhaust.add(c);
                    if(damageAmount <= 0){
                        damageAmount = 0;
                        break;
                    }
                }
            }
            for(AbstractCard c : cardsToExhaust){
                c.flash();
                dustCards.remove(c);
                AbstractDungeon.player.exhaustPile.moveToExhaustPile(c);
            }
            return damageAmount;
        }

        public void dustIt(boolean toTop, boolean exhaustIt){
            boolean exhaustSource = false;
            ArrayList<AbstractCard> playableCards = new ArrayList<>();
            for(AbstractCard c : dustCards){
                playableCards.add(c);
            }
            if(!playableCards.isEmpty()){
                Collections.shuffle(playableCards,AbstractDungeon.cardRandomRng.random);
                //过随机数
                AbstractDungeon.cardRandomRng.randomBoolean();
                AbstractCard lingeredCard = playableCards.get(0);
                exhaustSource = lingeredCard.exhaust || lingeredCard.exhaustOnUseOnce;

                if(lingeredCard instanceof AbstractTheresaCard){
                    ((AbstractTheresaCard) lingeredCard).triggerWhenLingered();

                    //部分卡牌可以阻止消耗
                    if(((AbstractTheresaCard) lingeredCard).dontExhaustIfExhaust)
                        exhaustSource = false;
                }

                //循历史而去，会阻止消耗牌的消耗
                AbstractPower ih = AbstractDungeon.player.getPower(IntoHistoryPower.POWER_ID);
                if(ih instanceof IntoHistoryPower){
                    ((IntoHistoryPower) ih).triggerCard(lingeredCard);
                    exhaustSource = false;
                }

                AbstractCard c = lingeredCard.makeSameInstanceOf();
                c.dontTriggerOnUseCard = true;
                c.damageTypeForTurn = DamageInfo.DamageType.THORNS;
                if(!toTop)
                    AbstractDungeon.actionManager.addToBottom(new PlayCardAction(c, null,true));
                else {
                    AbstractDungeon.actionManager.addToTop(new PlayCardAction(c, null,true));
                }
                if(exhaustIt || exhaustSource || lingeredCard.type == AbstractCard.CardType.POWER){
                    dustCards.remove(lingeredCard);
                    AbstractDungeon.player.exhaustPile.moveToExhaustPile(lingeredCard);
                }
            }
        }

        public void dustIt(boolean toTop){
            this.dustIt(toTop, false);
        }

        public boolean isFull(){
            return dustCards.size() >= dustUpLimit;
        }

        public boolean containsCard(AbstractCard c){
            return dustCards.contains(c);
        }

        public AbstractCard getInstanceCard(AbstractCard c){
            for(AbstractCard c2 : dustCards){
                if(c2.cardID == c.cardID && c2.uuid == c.uuid){
                    return c2;
                }
            }
            return null;
        }

        public void addCard(AbstractCard c){
            c.untip();
            c.unhover();
            c.stopGlowing();
            dustCards.add(c);

            for(AbstractPower p : AbstractDungeon.player.powers){
                if(p instanceof AbstractTheresaPower){
                    ((AbstractTheresaPower) p).triggerOnBecomeDust(c);
                }
            }

            if(c instanceof AbstractTheresaCard){
                ((AbstractTheresaCard) c).triggerWhenBecomeDust();
            }
        }

        public void removeCard(AbstractCard c){
            c.untip();
            c.unhover();
            c.stopGlowing();
            dustCards.remove(c);

            if(c instanceof AbstractTheresaCard){
                ((AbstractTheresaCard) c).triggerWhenNoLongerDust();
            }
        }

        private void tick(){
            //旋转周期为3
            rotateRate += Gdx.graphics.getDeltaTime()/4;
            while (rotateRate>=1F){
                rotateRate -= 1F;
            }
        }

        public void update(){
            tick();
            int cardIndex = 0;
            int cardSize = dustCards.size();
            for(AbstractCard c : dustCards){
                updateSingleCard(c,cardIndex,cardSize);
                cardIndex++;
            }
        }

        public void renderBefore(SpriteBatch sb){
            for(AbstractCard c : dustCards){
                if(CardScaleField.before.get(c))
                    c.render(sb);
            }
        }

        public void renderAfter(SpriteBatch sb){
            for(AbstractCard c : dustCards){
                if(!CardScaleField.before.get(c))
                    c.render(sb);
            }
        }

        private void updateSingleCard(AbstractCard c, int index, int size){
            if(AbstractDungeon.player == null)
                return;

            c.targetDrawScale = CIRCLE_DRAW_SCALE;

            float cardWidth = CIRCLE_DRAW_SCALE * AbstractCard.IMG_WIDTH;
            float width = AbstractDungeon.player.hb.width * 1.1F + cardWidth;
            float tmpWidth = 2F * cardWidth;
            width = Math.max(width, tmpWidth);
            float rRate = rotateRate + ((float)index/(float)size);
            while(rRate >= 1F){
                rRate -= 1F;
            }
            CardScaleField.before.set(c, rRate<0.5F);
            //position
            float xP = AbstractDungeon.player.drawX;
            float yP = AbstractDungeon.player.drawY;
            float xC = xP + (0.5F - 2*Math.abs(rRate-0.5F)) * width;
            c.target_x = xC;
            float k = 0.4F * width;
            float yOffset = 0.1F*width - k * (float) Math.pow(0.5F - 2*Math.abs(rRate-0.5F),2);
            if(rRate >= 0.5F)
                yOffset = -yOffset;
            c.target_y = yP - yOffset + Y_OFFSET;

            //scale
            float xScale = (float) (Math.sqrt(Math.pow(width/2F,2) - Math.pow(xC-xP,2)) / (width/2F));
            if(xScale<0)
                xScale = 0F;
//            if(rRate >= 0.5F)
//                xScale = -xScale;
            CardScaleField.xScale.set(c, xScale);

            //angle
            c.targetAngle = (0.5F - 2*Math.abs(rRate-0.5F)) * 60;

            if(AbstractDungeon.player.hb.hovered){
                int line = 0;
                cardWidth = 1.2F * PREVIEW_DRAW_SCALE * AbstractCard.IMG_WIDTH;
                if(size>4){
                    cardWidth *= 0.8F;
                    while(index>=5){
                        index -= 5;
                        line++;
                    }
                }
                width = cardWidth * (size>5?4:(size-1));

                c.targetDrawScale = PREVIEW_DRAW_SCALE;
                if(size>4)
                    c.targetDrawScale *= 0.8F;
                CardScaleField.before.set(c, true);
                CardScaleField.xScale.set(c, 1F);
                c.target_x = xP - 0.5F*width + index * cardWidth;
                c.target_y = (float)Settings.HEIGHT / 2.0F + 150F * Settings.scale;
                if(size>5){
                    c.target_y = (float)Settings.HEIGHT / 2.0F + 50F * Settings.scale + cardWidth * 1.2F * line;
                }
                c.targetAngle = 0F;
            }

            c.update();
        }
    }
}
