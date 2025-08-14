import { NextResponse } from 'next/server'

// This function can be marked `async` if using `await` inside
export function middleware(request) {
  const token = request.cookies.get('jwt')?.value;

  console.log(token);

  const protectedPaths = ['/produto', '/categoria', '/fornecedor', '/estoque']
  const isProtectedRoute = protectedPaths.some(path => request.nextUrl.pathname.startsWith(path))

  if (isProtectedRoute && !token) {
    const loginUrl = new URL('/auth/login', request.url)
    return NextResponse.redirect(loginUrl)
  }

  return NextResponse.next()
}

export const config ={
  matcher:['/produto/:path*', '/categoria/:path*', '/fornecedor/:path*', '/estoque/:path*']
}

