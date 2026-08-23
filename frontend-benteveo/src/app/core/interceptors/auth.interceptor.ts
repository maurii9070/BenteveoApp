import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

import { SessionService } from '../services/session.service';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const session = inject(SessionService);
  const token = session.token();

  if (!token) {
    return next(req);
  }

  return next(
    req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    }),
  );
}
