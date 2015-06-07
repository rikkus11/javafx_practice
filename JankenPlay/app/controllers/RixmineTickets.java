package controllers;

import java.util.List;
import java.util.Optional;

import models.entity.Ticket;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.rixmine.add_ticket;
import views.html.rixmine.detail_ticket;
import views.html.rixmine.rixmine;

public class RixmineTickets extends Controller {

    public static Result index() {
        List<Ticket> tickets = Ticket.find.all();

        return ok(rixmine.render(tickets));
    }

    public static Result showAddTicket() {
        return ok(add_ticket.render(Form.form(Ticket.class)));
    }

    public static Result addTicket() {
        Form<Ticket> f = Form.form(Ticket.class).bindFromRequest();

        if (f.hasErrors()) {
            return badRequest(add_ticket.render(f));
        }

        Ticket ticket = f.get();

        if (ticket.store()) {

            // 登録成功
            f.data().put("success", "true");

            return ok(add_ticket.render(f));
        } else {

            // 登録失敗。フォームにグローバルエラー追加
            f.reject("登録に失敗しました。もう一度やり直してください。");

            return badRequest(add_ticket.render(f));
        }
    }

    public static Result detailTicket(Long id) {
        Optional<Ticket> idTicket = new Ticket(id).unique();

        if (idTicket.isPresent()) {

            // 存在する場合はそのデータを詳細ページへ渡す
            return ok(detail_ticket.render(idTicket.get()));

        } else {

            // 存在しない場合はエラーページ･･･がないのでとりあえずメインページに返す。
            return redirect(routes.RixmineTickets.index());
        }
    }
}
