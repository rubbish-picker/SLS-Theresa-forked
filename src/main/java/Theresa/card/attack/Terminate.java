package Theresa.card.attack;

import Theresa.action.TerminateAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.DustPatch;
import Theresa.patch.SilkPatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Terminate extends AbstractTheresaCard {
    public static final String ID = "theresa:Terminate";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Terminate() {
        super(ID,cardStrings.NAME,-1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(isInAutoplay){
            calculateCardDamage(abstractMonster);
        }
        addToBot(new TheresaAttackAction(true));
        addToBot(new TerminateAction(abstractPlayer,freeToPlayOnce,energyOnUse));
        for(int i =0;i<magicNumber;i++)
            addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),skillEffect));
    }

    private int getCurrentEnergy(){
        int energy = EnergyPanel.getCurrentEnergy();
        if(isInAutoplay){
            energy = Math.max(energy, energyOnUse);
        }
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            energy += 2;
        }
        return energy;
    }

    private int getSilkAmount(){

        int amount = 0;
        for(AbstractCard c : AbstractDungeon.player.hand.group){
            if(SilkPatch.SilkCardField.silk.get(c) != null)
                amount++;
        }
        for(AbstractCard c : DustPatch.dustManager.dustCards){
            if(SilkPatch.SilkCardField.silk.get(c) != null)
                amount++;
        }
        return amount;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage += getCurrentEnergy() * getSilkAmount();
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        baseDamage += getCurrentEnergy() * getSilkAmount();
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(-1);
            upgradeMagicNumber(1);
        }
    }
}

