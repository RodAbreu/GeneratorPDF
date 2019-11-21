// Generated from casamento.g4 by ANTLR 4.7.2
package br.ufscar.dc.cc2.casamentoparser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link casamentoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface casamentoVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link casamentoParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(casamentoParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#titulo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTitulo(casamentoParser.TituloContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#numConvidados}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumConvidados(casamentoParser.NumConvidadosContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData(casamentoParser.DataContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#listaPadrinhos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaPadrinhos(casamentoParser.ListaPadrinhosContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#padrinho}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPadrinho(casamentoParser.PadrinhoContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#listaPresentes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaPresentes(casamentoParser.ListaPresentesContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#presente}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPresente(casamentoParser.PresenteContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#listaConvidados}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaConvidados(casamentoParser.ListaConvidadosContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#listaServicos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaServicos(casamentoParser.ListaServicosContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#fotografo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFotografo(casamentoParser.FotografoContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#buffet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuffet(casamentoParser.BuffetContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#cerimonial}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCerimonial(casamentoParser.CerimonialContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#local}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocal(casamentoParser.LocalContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#musica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMusica(casamentoParser.MusicaContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#decoracao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecoracao(casamentoParser.DecoracaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#convites}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConvites(casamentoParser.ConvitesContext ctx);
	/**
	 * Visit a parse tree produced by {@link casamentoParser#lua_de_mel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLua_de_mel(casamentoParser.Lua_de_melContext ctx);
}