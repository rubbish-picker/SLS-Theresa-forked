package Theresa.card.attack;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.debuff.DyingPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Hardship extends AbstractTheresaCard {
    public static final String ID = "theresa:Hardship";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Hardship() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 30;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn,skillEffect));
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage = baseMagicNumber;
        super.applyPowers();
        magicNumber = damage;
        baseDamage = tmp;
        isMagicNumberModified = magicNumber != baseMagicNumber;
        super.applyPowers();
    }

    @SpirePatch(clz = AbstractCard.class,method = "calculateCardDamage")
    public static class DamageAllEnemiesActionPatch {
        @SpireInsertPatch(rloc = 65,localvars = {"tmp"})
        public static void Insert(AbstractCard _inst, AbstractMonster mo, float[] tmp) {
            if(_inst instanceof Hardship){
                for(int i =0;i<tmp.length;i++){
                    if(AbstractDungeon.getCurrRoom().monsters.monsters.size()>i){
                        AbstractMonster mon = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                        if(!mon.isDeadOrEscaped()){
                            AbstractPower dying = mon.getPower(DyingPower.POWER_ID);
                            if(dying instanceof DyingPower && ((DyingPower) dying).reachMax()){
                                tmp[i] = _inst.baseMagicNumber;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(10);
        }
    }
}



