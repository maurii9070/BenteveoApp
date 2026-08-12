import { HttpErrorResponse, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

import { ApiError, ApiResponse } from '../../shared/models/api-response';

export function apiErrorInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  return next(req).pipe(
    catchError((error: unknown) => throwError(() => toApiError(error))),
  );
}

function toApiError(error: unknown): ApiError {
  if (error instanceof HttpErrorResponse) {
    const body = error.error as ApiResponse<unknown> | undefined;
    const message = body?.message ?? statusMessage(error.status);
    return new ApiError(message, error.status);
  }
  return new ApiError('Ocurrió un error inesperado.');
}

function statusMessage(status: number): string {
  if (status === 0) {
    return 'No se pudo conectar con el servidor.';
  }
  return 'Ocurrió un error inesperado.';
}