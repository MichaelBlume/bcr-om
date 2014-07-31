This project attempts to provide a starting point for those who want to work
through the Om tutorial using nrepl and vim/emacs rather than light table.

To use run ```lein do cljsbuild once, repl```

next open src/clj/start_bcr.clj in vim/emacs. Eval forms one by one as
instructed. When instructed, open http://localhost:8080 in your favorite
browser (I recommend firefox)

open src/cljs/om_stuff.cljs. Try evaluating the om/root form. You should see
hello world show up in your browser. Proceed through the tutorial from there.

Much of this project is stolen from
https://github.com/cemerick/austin/tree/master/browser-connected-repl-sample

this project is licensed under epl, so hopefully cemerick won't mind =)
